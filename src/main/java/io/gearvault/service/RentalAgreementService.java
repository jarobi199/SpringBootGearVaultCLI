package io.gearvault.service;

import io.gearvault.model.Equipment;
import io.gearvault.model.RentalAgreement;
import io.gearvault.repository.RentalAgreementRepository;
import io.gearvault.util.InputHandler;
import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class RentalAgreementService {

    @Autowired
    private RentalAgreementRepository rentalAgreementRepository;

    public void checkOutEquipment(Equipment equipment, String name, String phone, LocalDate dueDate) {
        //TODO: add checkout equipment code here
    }

    public void viewRentalHistory(Equipment equipment) {
        List<RentalAgreement> rentalAgreements =  rentalAgreementRepository.findByEquipmentId(equipment.getId());
        System.out.println("| RENTAL HISTORY |");
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
                    rentalAgreement.getReturnDate().toString(), InputHandler.formatAsMoney(rentalAgreement.getDeposit()), rentalAgreement.getStatus().name(), InputHandler.formatAsMoney(rentalAgreement.getTotalCost()));
        });
        table.render();
    }
}
