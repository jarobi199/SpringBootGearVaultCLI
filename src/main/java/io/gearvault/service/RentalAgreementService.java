package io.gearvault.service;

import io.gearvault.model.Equipment;
import io.gearvault.model.RentalAgreement;
import io.gearvault.repository.RentalAgreementRepository;
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
        //TODO: add code here
    }
}
