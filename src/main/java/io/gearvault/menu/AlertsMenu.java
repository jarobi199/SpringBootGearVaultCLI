package io.gearvault.menu;

import io.gearvault.enums.AlertType;
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
                case 2 -> viewOverdueAlerts();
                case 3 -> viewMaintenanceDueAlerts();
                case 4 -> viewWarrantyExpiringAlerts();
            }
        }
        while (choice != 0);
    }

    public void viewWarrantyExpiringAlerts() {
        alertService.displayAlerts(AlertType.WARRANTY_EXPIRING);
    }

    public void viewMaintenanceDueAlerts() {
        alertService.displayAlerts(AlertType.MAINTENANCE_DUE);
    }

    public void viewOverdueAlerts() {
        alertService.displayAlerts(AlertType.OVERDUE_RETURN);
    }

    public void viewAllAlerts() {
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
