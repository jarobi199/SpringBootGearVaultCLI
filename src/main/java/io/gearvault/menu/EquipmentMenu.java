package io.gearvault.menu;

import io.gearvault.enums.*;
import io.gearvault.interfaces.IMenu;
import io.gearvault.model.Equipment;
import io.gearvault.model.EventEquipment;
import io.gearvault.service.EquipmentService;
import io.gearvault.util.InputHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class EquipmentMenu implements IMenu {

    @Autowired
    private EquipmentService equipmentService;

    @Override
    public void show() {
        int choice;
        do {
            printOptions();
            choice = InputHandler.getIntegerInput();
            switch (choice) {
                case 1 -> listAllEquipment();
                case 2 -> listAllAvailableEquipment();
                case 3 -> addEquipment();
                case 6 -> addBookingNote();
                case 7 -> retireEquipment();
            }
        }
        while (choice != 0);
    }

    public void retireEquipment() {
        Equipment equipment = listEquipmentAndSelect(null);
        System.out.println("Are you sure that you want to retire this equipment? (Y/N)?:");
        boolean answer = InputHandler.getBooleanInput();
        if(answer) {
            equipmentService.retireEquipment(equipment);
        }
    }

    public void addBookingNote() {
        EventEquipment eventEquipment = (EventEquipment) listEquipmentAndSelect(EquipmentType.EVENT_EQUIPMENT);
        if(eventEquipment != null) {
            System.out.println("Enter the event date (yyyy-MM-dd):");
            LocalDate eventDate = InputHandler.getDateInput();
            System.out.println("Enter the setup details:");
            String setupDetails = InputHandler.getStringInput();
            System.out.println("Enter the headcount:");
            int headcount = InputHandler.getIntegerInput();
            System.out.println("Enter the notes:");
            String notes = InputHandler.getStringInput();

            equipmentService.addBookingNote(eventEquipment, eventDate, setupDetails, headcount, notes);
            System.out.println("The booking note has been added successfully!");
        }
    }

    public void listAllAvailableEquipment() {
        equipmentService.listEquipment(EquipmentStatus.AVAILABLE);
    }

    public void addEquipment() {
        System.out.println("Select the equipment type (POWER_TOOL, HEAVY_MACHINERY, EVENT_EQUIPMENT):");
        EquipmentType equipmentType = EquipmentType.valueOf(InputHandler.getStringInput().toUpperCase());
        System.out.println("Enter the name:");
        String name = InputHandler.getStringInput();
        System.out.println("Enter the brand:");
        String brand = InputHandler.getStringInput();
        System.out.println("Enter the model:");
        String model = InputHandler.getStringInput();
        System.out.println("Enter the serial number:");
        String serial = InputHandler.getStringInput();
        System.out.println("Enter the purchase price:");
        double purchasePrice = InputHandler.getDoubleInput();
        System.out.println("Enter the purchase date (yyyy-MM-dd):");
        LocalDate purchaseDate = InputHandler.getDateInput();
        System.out.println("Enter the warranty expiry date (yyyy-MM-dd):");
        LocalDate warrantyExpiryDate = InputHandler.getDateInput();
        System.out.println("Enter the condition (EXCELLENT, GOOD, FAIR, POOR, NEEDS_REPAIR):");
        EquipmentCondition condition = EquipmentCondition.valueOf(InputHandler.getStringInput().toUpperCase());
        System.out.println("Enter the notes:");
        String notes = InputHandler.getStringInput();

        switch (equipmentType) {
            case POWER_TOOL -> {
                System.out.println("Is this power tool battery powered (Y/N)?:");
                boolean isBatteryPowered = InputHandler.getBooleanInput();
                System.out.println("Enter the voltage:");
                int voltage = InputHandler.getIntegerInput();
                System.out.println("Enter the tool class(DRILL, SAW, SANDER, NAIL_GUN, OTHER):");
                ToolClass toolClass = ToolClass.valueOf(InputHandler.getStringInput().toUpperCase());
                System.out.println("Is this power tool require a charge check (Y/N)?:");
                boolean requiresChargeCheck = InputHandler.getBooleanInput();
                System.out.println("Enter the hourly rate:");
                double hourlyRate = InputHandler.getDoubleInput();
                System.out.println("Enter the daily rate:");
                double dailyRate = InputHandler.getDoubleInput();

                equipmentService.addPowerTool(name, brand, model, serial, purchasePrice, purchaseDate, warrantyExpiryDate, condition, notes, isBatteryPowered, voltage, toolClass, requiresChargeCheck, hourlyRate, dailyRate);
                System.out.println("The power tool has been added successfully!");
            }
            case EVENT_EQUIPMENT -> {
                System.out.println("Enter the event category (TENT, TABLE_CHAIR, LIGHTING, AUDIO, OTHER):");
                EventCategory eventCategory = EventCategory.valueOf(InputHandler.getStringInput().toUpperCase());
                System.out.println("Enter the quantity");
                int quantity = InputHandler.getIntegerInput();
                System.out.println("Is there any setup required (Y/N)?:");
                boolean setup = InputHandler.getBooleanInput();
                System.out.println("Enter the flat event rate:");
                double flatEventRate = InputHandler.getDoubleInput();

                equipmentService.addEventEquipment(name, brand, model, serial, purchasePrice, purchaseDate, warrantyExpiryDate, condition, notes, eventCategory, quantity, setup, flatEventRate);
                System.out.println("The event equipment has been added successfully!");
            }
            case HEAVY_MACHINERY -> {
                System.out.println("Enter the engine hours");
                int engineHours = InputHandler.getIntegerInput();
                System.out.println("Does this heavy machinery require an operator certification (Y/N)?:");
                boolean requiresCert= InputHandler.getBooleanInput();
                System.out.println("Enter the fuel type (DIESEL, GASOLINE, ELECTRIC, PROPANE):");
                FuelType fuelType = FuelType.valueOf(InputHandler.getStringInput().toUpperCase());
                System.out.println("Enter the daily rate:");
                double dailyRate = InputHandler.getDoubleInput();
                System.out.println("Enter the deposit:");
                double deposit = InputHandler.getDoubleInput();

                equipmentService.addHeavyMachinery(name, brand, model, serial, purchasePrice, purchaseDate, warrantyExpiryDate, condition, notes, engineHours, requiresCert, fuelType, dailyRate, deposit);
                System.out.println("The heavy machinery equipment has been added successfully!");
            }
        }
    }

    public void listAllEquipment() {
        equipmentService.listEquipment(null);
    }

    public Equipment listEquipmentAndSelect(EquipmentType equipmentType) {
        int number = 1;
        Equipment equipment = null;
        int choice = 0;
        List<Equipment> equipmentList = null;

        if(equipmentType != null) {
            equipmentList = equipmentService.findAllEquipment().stream().filter(e -> e.getEquipmentType().equals(equipmentType)).toList();
        }
        else
        {
            equipmentList = equipmentService.findAllEquipment();
        }

        if (!equipmentList.isEmpty()) {
            for (Equipment e : equipmentList) {
                System.out.println("[" + number + "] " +  e.getBrand() + " " + e.getName());
                number++;
            }
            System.out.println("Select equipment:");
            choice = InputHandler.getIntegerInput();
            equipment = equipmentList.get(choice - 1);
        }
        else
        {
            System.out.println("There is no equipment available.");
        }

        return equipment;
    }

    @Override
    public void printOptions() {
        System.out.println();
        System.out.println("[1] List all equipment");
        System.out.println("[2] List all available equipment");
        System.out.println("[3] Add equipment");
        System.out.println("[4] View equipment details");
        System.out.println("[5] Edit equipment");
        System.out.println("[6] Add booking note");
        System.out.println("[7] Retire equipment");
        System.out.println("[0] Back");
    }
}
