package io.gearvault.menu;

import io.gearvault.enums.EquipmentCondition;
import io.gearvault.enums.EquipmentStatus;
import io.gearvault.enums.RentalStatus;
import io.gearvault.interfaces.IMenu;
import io.gearvault.model.Equipment;
import io.gearvault.model.HeavyMachinery;
import io.gearvault.model.RentalAgreement;
import io.gearvault.service.RentalAgreementService;
import io.gearvault.util.InputHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

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
                case 2 -> checkInEquipment();
                case 3 -> viewActiveRentals();
                case 4 -> viewOverdueRentals();
                case 5 -> viewRentalHistory();
            }
        }
        while (choice != 0);
    }

    public void viewOverdueRentals() {
        rentalAgreementService.viewOverdueRentals();
    }

    public void viewRentalHistory() {
        rentalAgreementService.viewRentalHistory();
    }

    public void checkInEquipment() {
        RentalAgreement rentalAgreement =  listRentalAgreementsAndSelect();
        System.out.println("What is the condition of the unit?:");
        EquipmentCondition equipmentCondition = EquipmentCondition.valueOf(InputHandler.getStringInput().toUpperCase());
        rentalAgreementService.checkInEquipment(rentalAgreement, equipmentCondition);
    }

    public void viewActiveRentals() {
        rentalAgreementService.viewActiveRentals();
    }

    public void checkOutEquipment() {
        Equipment equipment = equipmentMenu.listEquipmentAndSelect(null);
        boolean answer = true;

        if (EquipmentStatus.AVAILABLE.equals(equipment.getStatus())) {
            if ((equipment instanceof HeavyMachinery heavyMachinery) && heavyMachinery.isRequiresOperatorCert()) {
                System.out.println(heavyMachinery.getCertWarning());
                System.out.println("Do you have the required credentials to proceed (Y/N)?:");
                answer = InputHandler.getBooleanInput();
            }

            if (answer) {
                System.out.println("Please enter the customer name:");
                String name = InputHandler.getStringInput();
                System.out.println("Please enter the customer phone:");
                String phone = InputHandler.getStringInput();
                System.out.println("Enter the due date (yyyy-MM-dd):");
                LocalDate dueDate = InputHandler.getDateInput();

                rentalAgreementService.checkOutEquipment(equipment, name, phone, dueDate);
            }
        }
        else
        {
            System.out.println("Only equipment that is marked as available can be checked out!");
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

    public RentalAgreement listRentalAgreementsAndSelect() {
        int number = 1;
        RentalAgreement rentalAgreement = null;
        int choice = 0;
        List<RentalAgreement> rentalAgreementList = null;

        rentalAgreementList = rentalAgreementService.findAllRentalsByStatus(RentalStatus.ACTIVE);

        if (!rentalAgreementList.isEmpty()) {
            for (RentalAgreement r : rentalAgreementList) {
                System.out.println("[" + number + "] " +  r.getCheckOutDate() + " - " + r.getEquipmentBrand() + " " + r.getEquipmentModel() + " (" + r.getEquipmentName() + ")");
                number++;
            }
            System.out.println("Select a rental agreement:");
            choice = InputHandler.getIntegerInput();
            rentalAgreement = rentalAgreementList.get(choice - 1);
        }
        else
        {
            System.out.println("There are no rental agreements available.");
        }

        return rentalAgreement;
    }

}
