package io.gearvault.menu;

import io.gearvault.enums.ServiceType;
import io.gearvault.interfaces.IMenu;
import io.gearvault.model.Equipment;
import io.gearvault.model.MaintenanceLog;
import io.gearvault.service.MaintenanceLogService;
import io.gearvault.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

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
                case 3 -> viewAllMaintenanceRecords();
                case 4 -> deleteMaintenanceRecord();
            }
        }
        while (choice != 0);
    }

    private void viewAllMaintenanceRecords() {
        maintenanceLogService.viewAllMaintenanceRecords();
    }

    public void deleteMaintenanceRecord() {
        Equipment equipment = equipmentMenu.listEquipmentAndSelect(null);
        if (equipment != null) {
            MaintenanceLog maintenanceLog = listMaintenanceLogsAndSelect(equipment);
            if(maintenanceLog != null) {
                System.out.println("Are you sure that you want to delete this maintenance log? (Y/N)?:");
                boolean answer = InputHandler.getBooleanInput();
                if(answer) {
                    maintenanceLogService.deleteMaintenanceLog(maintenanceLog);
                    System.out.println("Maintenance log has been successfully deleted!");
                }
            }
        }
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

    public MaintenanceLog listMaintenanceLogsAndSelect(Equipment equipment) {
        int number = 1;
        MaintenanceLog maintenanceLog = null;
        int choice = 0;

        List<MaintenanceLog> maintenanceLogList = maintenanceLogService.findMaintenanceLogsByEquipmentId(equipment);
        if (!maintenanceLogList.isEmpty()) {
            for (MaintenanceLog m : maintenanceLogList) {
                System.out.println("[" + number + "] " +  " " + m.getServiceDate().toString() + " - " + m.getServiceType().name() + " - " +  m.getDescription());
                number++;
            }
            System.out.println("Select a maintenance log:");
            choice = InputHandler.getIntegerInput();
            maintenanceLog = maintenanceLogList.get(choice - 1);
        }
        else
        {
            System.out.println("There are no maintenance logs.");
        }

        return maintenanceLog;
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
