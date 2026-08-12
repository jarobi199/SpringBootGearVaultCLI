package io.gearvault.menu;

import io.gearvault.interfaces.IMenu;
import io.gearvault.service.ReportService;
import io.gearvault.util.InputHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportsMenu implements IMenu {

    @Autowired
    private ReportService reportService;

    @Override
    public void show() {
        int choice;
        do {
            printOptions();
            choice = InputHandler.getIntegerInput();
            switch (choice) {
                case 1 -> revenueReport();
                case 2 -> utilizationReport();
                case 3 -> overdueReport();
                case 4 -> maintenanceCostByType();
            }
        }
        while (choice != 0);
    }

    public void maintenanceCostByType() {
        reportService.maintenanceCostByType();
    }

    public void overdueReport() {
        reportService.createOverdueReport();
    }

    public void utilizationReport() {
        reportService.createUtilizationReport();
    }

    public void revenueReport() {
        reportService.createRevenueReport();
    }

    @Override
    public void printOptions() {
        System.out.println();
        System.out.println("[1] Revenue report");
        System.out.println("[2] Utilization report");
        System.out.println("[3] Overdue report");
        System.out.println("[4] Maintenance cost by type");
        System.out.println("[0] Back");
    }

}