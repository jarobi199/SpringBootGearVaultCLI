package io.gearvault.service;

import io.gearvault.authentication.SessionContext;
import io.gearvault.enums.EquipmentCondition;
import io.gearvault.enums.EquipmentStatus;
import io.gearvault.enums.EquipmentType;
import io.gearvault.enums.RentalStatus;
import io.gearvault.interfaces.AlertStrategy;
import io.gearvault.model.Equipment;
import io.gearvault.model.HeavyMachinery;
import io.gearvault.model.RentalAgreement;
import io.gearvault.repository.EquipmentRepository;
import io.gearvault.repository.RentalAgreementRepository;
import io.gearvault.util.InputHandler;
import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class RentalAgreementService {

    @Autowired
    private RentalAgreementRepository rentalAgreementRepository;
    @Autowired
    private EquipmentRepository equipmentRepository;

    public void checkOutEquipment(Equipment equipment, String name, String phone, LocalDate dueDate) {
        double depositAmount = EquipmentType.HEAVY_MACHINERY.equals(equipment.getEquipmentType()) ? ((HeavyMachinery) equipment).getDeposit() : 0.0;
        long days = ChronoUnit.DAYS.between(LocalDate.now(), dueDate);
        equipment.setStatus(EquipmentStatus.RENTED);
        RentalAgreement  rentalAgreement = new RentalAgreement(SessionContext.getUser().getId(), equipment.getId(), equipment.getName(), equipment.getSerialNumber(),
                equipment.getBrand(), equipment.getModel(), name, phone, LocalDate.now(), dueDate, null, depositAmount, RentalStatus.ACTIVE, days);

        rentalAgreementRepository.save(rentalAgreement);
        equipmentRepository.save(equipment);
    }

    public void viewRentalHistory(Equipment equipment) {
        List<RentalAgreement> rentalAgreements =  rentalAgreementRepository.findByEquipmentId(equipment.getId());
        System.out.println("| RENTAL HISTORY |");
        displayRentals(rentalAgreements);
    }

    private void displayRentals(List<RentalAgreement> rentalAgreements ) {
        Table table = Clique.table(TableType.BOX_DRAW)
                .headers(
                        "[*blue, bold]EQUIPMENT NAME[/]",
                        "[*blue, bold]EQUIPMENT SERIAL NUMBER[/]",
                        "[*blue, bold]EQUIPMENT BRAND[/]",
                        "[*blue, bold]EQUIPMENT MODEL[/]",
                        "[*blue, bold]CUSTOMER NAME[/]",
                        "[*blue, bold]CUSTOMER PHONE[/]",
                        "[*blue, bold]CHECK OUT DATE[/]",
                        "[*blue, bold]DUE DATE[/]",
                        "[*blue, bold]RETURN DATE[/]",
                        "[*blue, bold]DEPOSIT[/]",
                        "[*blue, bold]STATUS[/]",
                        "[*blue, bold]TOTAL COST[/]"
                );
        rentalAgreements.forEach(rentalAgreement -> {
            table.row(rentalAgreement.getEquipmentName(), rentalAgreement.getEquipmentSerialNumber(), rentalAgreement.getEquipmentBrand(), rentalAgreement.getEquipmentModel(), rentalAgreement.getCustomerName(),
                    rentalAgreement.getCustomerPhone(), rentalAgreement.getCheckOutDate().toString(), rentalAgreement.getDueDate().toString(),
                    (rentalAgreement.getReturnDate() != null) ? rentalAgreement.getReturnDate().toString() : "N/A", InputHandler.formatAsMoney(rentalAgreement.getDeposit()),
                    rentalAgreement.getStatus().name(), InputHandler.formatAsMoney(rentalAgreement.getTotalCost()));
        });
        table.render();
    }

    public void viewActiveRentals() {
        List<RentalAgreement> rentalAgreements =  rentalAgreementRepository.findByUserIdAndStatus(SessionContext.getUser().getId(), RentalStatus.ACTIVE);
        displayRentals(rentalAgreements);
    }

    public List<RentalAgreement> findAllRentalsByStatus(RentalStatus rentalStatus) {
        return rentalAgreementRepository.findByUserIdAndStatus(SessionContext.getUser().getId(), rentalStatus);
    }

    public void checkInEquipment(RentalAgreement rentalAgreement, EquipmentCondition equipmentCondition) {
        Optional<Equipment> optionalEquipment = equipmentRepository.findById(rentalAgreement.getEquipmentId());
        optionalEquipment.ifPresent(equipment -> {
            rentalAgreement.setReturnDate(LocalDate.now());
            rentalAgreement.setStatus(RentalStatus.RETURNED);
            equipment.setStatus(EquipmentStatus.AVAILABLE);
            equipment.setCondition(equipmentCondition);

            equipmentRepository.save(equipment);
            rentalAgreementRepository.save(rentalAgreement);
        });

        if (rentalAgreement.getReturnDate().isAfter(rentalAgreement.getDueDate())) {
            long lateDays = ChronoUnit.DAYS.between(rentalAgreement.getDueDate(), rentalAgreement.getReturnDate());
            System.out.println(AlertStrategy.ANSI_YELLOW + "This equipment was " + lateDays + " overdue!" + AlertStrategy.ANSI_RESET);
        }
    }

    public void viewRentalHistory() {
        List<RentalAgreement> rentalAgreements =  rentalAgreementRepository.findByUserId(SessionContext.getUser().getId());
        System.out.println("| USER RENTAL HISTORY |");
        displayRentals(rentalAgreements);
    }

    public void viewOverdueRentals() {
        List<RentalAgreement> overdueRentals = rentalAgreementRepository.findByUserIdAndStatus(SessionContext.getUser().getId(), RentalStatus.ACTIVE)
                .stream()
                .filter(rentalAgreement -> rentalAgreement.getDueDate().isBefore(LocalDate.now().plusDays(SessionContext.getUser().getOverdueGraceDays()))).toList();
        displayRentals(overdueRentals);
    }
}
