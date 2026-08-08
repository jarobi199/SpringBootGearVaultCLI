package io.gearvault.menu;

import io.gearvault.interfaces.IMenu;
import io.gearvault.service.AlertService;
import io.gearvault.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AlertsMenu implements IMenu {

    @Autowired
    private AlertService alertService;

    @Override
    public void show() {
        int choice;
        do {
            printOptions();
            choice = InputHandler.getIntegerInput();
            switch (choice) {
                case 1 -> viewAllAlerts();
            }
        }
        while (choice != 0);
    }

    private void viewAllAlerts() {
        alertService.displayAlerts(null);
    }

    @Override
    public void printOptions() {
        System.out.println();
        System.out.println("[1] View all alerts");
        System.out.println("[2] Overdue return alerts");
        System.out.println("[3] Maintenance due alerts");
        System.out.println("[4] Warranty expiring alerts");
        System.out.println("[0] Back");
    }
}
