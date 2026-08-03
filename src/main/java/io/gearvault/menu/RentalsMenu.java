package io.gearvault.menu;

import io.gearvault.interfaces.IMenu;
import io.gearvault.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RentalsMenu implements IMenu {

    @Autowired
    private EquipmentMenu equipmentMenu;

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
