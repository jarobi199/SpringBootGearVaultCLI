package io.gearvault.menu;

import io.gearvault.enums.ServiceType;
import io.gearvault.interfaces.IMenu;
import io.gearvault.model.Equipment;
import io.gearvault.service.MaintenanceLogService;
import io.gearvault.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MaintenanceMenu implements IMenu {

    @Autowired
    private EquipmentMenu equipmentMenu;
    @Autowired
    private MaintenanceLogService maintenanceLogService;

    @Override
    public void show() {
        int choice;
        do {
            printOptions();
            choice = InputHandler.getIntegerInput();
            switch (choice) {
                case 1 -> logMaintenanceRecord();
                case 2 -> viewHistoryByEquipment();
            }
        }
        while (choice != 0);
    }

    public void viewHistoryByEquipment() {
        Equipment equipment = equipmentMenu.listEquipmentAndSelect(null);
        maintenanceLogService.viewHistory(equipment);
    }

    public void logMaintenanceRecord() {
        Equipment equipment = equipmentMenu.listEquipmentAndSelect(null);
        System.out.println("Enter the service date (yyyy-MM-dd):");
        LocalDate serviceDate = InputHandler.getDateInput();
        System.out.println("Enter the service type");
        System.out.println("(CLEANING, BLADE_BIT_REPLACEMENT, ENGINE_SERVICE, CALIBRATION");
        System.out.println("SAFETY_INSPECTION, REPAIR, OTHER):");
        ServiceType serviceType = ServiceType.valueOf(InputHandler.getStringInput().toUpperCase());
        System.out.println("Enter the description:");
        String description = InputHandler.getStringInput();
        System.out.println("Enter the cost:");
        double cost = InputHandler.getDoubleInput();
        System.out.println("Enter the number of hours at service:");
        int hours = InputHandler.getIntegerInput();
        System.out.println("Enter the name of the technician");
        String name = InputHandler.getStringInput();

        maintenanceLogService.addMaintenanceLog(equipment, serviceDate, serviceType, description, cost, hours, name);
    }

    @Override
    public void printOptions() {
        System.out.println();
        System.out.println("[1] Log maintenance record");
        System.out.println("[2] View history by equipment");
        System.out.println("[3] View all recent records");
        System.out.println("[4] Delete record");
        System.out.println("[0] Back");
    }

}
