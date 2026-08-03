package io.gearvault.menu;

import io.gearvault.interfaces.IMenu;
import io.gearvault.model.Equipment;
import io.gearvault.model.HeavyMachinery;
import io.gearvault.service.RentalAgreementService;
import io.gearvault.util.InputHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class RentalsMenu implements IMenu {

    @Autowired
    private EquipmentMenu equipmentMenu;
    @Autowired
    private RentalAgreementService rentalAgreementService;

    @Override
    public void show() {
        int choice;
        do {
            printOptions();
            choice = InputHandler.getIntegerInput();
            switch (choice) {
                case 1 -> checkOutEquipment();
            }
        }
        while (choice != 0);
    }

    public void checkOutEquipment() {
        Equipment equipment = equipmentMenu.listEquipmentAndSelect(null);
        boolean answer = true;

        if((equipment instanceof HeavyMachinery heavyMachinery) && heavyMachinery.isRequiresOperatorCert()) {
            System.out.println(heavyMachinery.getCertWarning());
            System.out.println("Do you have the required credentials to proceed (Y/N)?:");
            answer = InputHandler.getBooleanInput();
        }

        if(answer) {
            System.out.println("Please enter the customer name:");
            String name = InputHandler.getStringInput();
            System.out.println("Please enter the customer phone:");
            String phone = InputHandler.getStringInput();
            System.out.println("Enter the due date (yyyy-MM-dd):");
            LocalDate dueDate = InputHandler.getDateInput();

            rentalAgreementService.checkOutEquipment(equipment, name, phone, dueDate);
        }
    }

    @Override
    public void printOptions() {
        System.out.println();
        System.out.println("[1] Check out equipment");
        System.out.println("[2] Check in equipment");
        System.out.println("[3] View active rentals");
        System.out.println("[4] View overdue rentals");
        System.out.println("[5] View rental history");
        System.out.println("[0] Back");
    }

}
