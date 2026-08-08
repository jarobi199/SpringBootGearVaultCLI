package io.gearvault.menu;

import io.gearvault.interfaces.IMenu;
import io.gearvault.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MainMenu implements IMenu {

    @Autowired
    private AuthenticateMenu authenticateMenu;
    @Autowired
    private SettingsMenu settingsMenu;
    @Autowired
    private EquipmentMenu equipmentMenu;
    @Autowired
    private MaintenanceMenu maintenanceMenu;
    @Autowired
    private RentalsMenu rentalsMenu;
    @Autowired
    private AlertsMenu alertsMenu;
    @Autowired
    private GoodbyeMenu goodbyeMenu;

    public void show() {
        int choice = 0;
        IMenu menu;

        System.out.println();
        displayTitle();
        authenticateMenu.automaticLogin();
        System.out.println();

        do {
            printOptions();
            choice = InputHandler.getIntegerInput();
            menu = switch (choice) {
                case 1 -> equipmentMenu;
                case 2 -> rentalsMenu;
                case 3 -> maintenanceMenu;
                case 5 -> alertsMenu;
                case 6 -> settingsMenu;
                case 0 -> goodbyeMenu;
               default -> throw new IllegalStateException("Unexpected value: " + choice);
            };
            menu.show();
        }
        while (choice != 0);

        InputHandler.closeInput();
    }

    public void printOptions() {
        System.out.println("[1] Equipment");
        System.out.println("[2] Rentals");
        System.out.println("[3] Maintenance");
        System.out.println("[4] Reports");
        System.out.println("[5] Alerts");
        System.out.println("[6] Settings");
        System.out.println("[0] Exit");
        System.out.println("Please make a selection:");
    }

    public void displayTitle() {
        System.out.println("===============================================================");
        System.out.println("    Welcome to the Gear Vault Equipment Rental Application!");
        System.out.println("================================================================");
    }

}
