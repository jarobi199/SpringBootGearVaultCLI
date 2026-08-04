package io.gearvault.service;

import io.gearvault.authentication.SessionContext;
import io.gearvault.enums.EquipmentStatus;
import io.gearvault.enums.EquipmentType;
import io.gearvault.enums.RentalStatus;
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
        RentalAgreement  rentalAgreement = new RentalAgreement(SessionContext.getUser().getId(), equipment.getId(), name, phone,
                                                                                                            LocalDate.now(), dueDate, null, depositAmount, RentalStatus.ACTIVE, days);

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
            table.row(rentalAgreement.getCustomerName(), rentalAgreement.getCustomerPhone(), rentalAgreement.getCheckOutDate().toString(), rentalAgreement.getDueDate().toString(),
                    (rentalAgreement.getReturnDate() != null) ? rentalAgreement.getReturnDate().toString() : "N/A", InputHandler.formatAsMoney(rentalAgreement.getDeposit()), rentalAgreement.getStatus().name(), InputHandler.formatAsMoney(rentalAgreement.getTotalCost()));
        });
        table.render();
    }

    public void viewActiveRentals() {
        List<RentalAgreement> rentalAgreements =  rentalAgreementRepository.findByUserIdAndStatus(SessionContext.getUser().getId(), RentalStatus.ACTIVE);
        displayRentals(rentalAgreements);
    }

}
