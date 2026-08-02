package io.gearvault.service;

import io.gearvault.authentication.SessionContext;
import io.gearvault.enums.*;
import io.gearvault.model.*;
import io.gearvault.repository.EquipmentRepository;
import io.gearvault.util.InputHandler;
import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EquipmentService {
    @Autowired
    private EquipmentRepository equipmentRepository;

    public List<Equipment> findAllEquipment() {
        return equipmentRepository.findByUserId(SessionContext.getUser().getId());
    }

    public void addPowerTool(String name, String brand, String model, String serial, double purchasePrice, LocalDate purchaseDate, LocalDate warrantyExpiryDate, EquipmentCondition condition,
                             String notes, boolean isBatteryPowered, int voltage, ToolClass toolClass, boolean requiresChargeCheck, double hourlyRate, double dailyRate) {
        PowerTool powerTool = new PowerTool(SessionContext.getUser().getId(), name, brand, model, serial, purchasePrice, purchaseDate, warrantyExpiryDate, condition, EquipmentStatus.AVAILABLE,
                                                                                notes, isBatteryPowered, voltage, toolClass, requiresChargeCheck, hourlyRate, dailyRate);
        equipmentRepository.save(powerTool);
    }

    public void addEventEquipment( String name, String brand, String model, String serial, double purchasePrice, LocalDate purchaseDate, LocalDate warrantyExpiryDate, EquipmentCondition condition,
                                  String notes, EventCategory eventCategory, int quantity, boolean setup, double flatEventRate) {
        EventEquipment eventEquipment = new EventEquipment(SessionContext.getUser().getId(), name, brand, model, serial, purchasePrice, purchaseDate, warrantyExpiryDate, condition, EquipmentStatus.AVAILABLE,
                                                                    notes, eventCategory, quantity, setup, flatEventRate);
        equipmentRepository.save(eventEquipment);
    }

    public void addHeavyMachinery( String name, String brand, String model, String serial, double purchasePrice, LocalDate purchaseDate, LocalDate warrantyExpiryDate, EquipmentCondition condition,
                                  String notes, int engineHours, boolean requiresCert, FuelType fuelType, double dailyRate, double deposit) {
        HeavyMachinery heavyMachinery = new HeavyMachinery(SessionContext.getUser().getId(), name, brand, model, serial, purchasePrice, purchaseDate, warrantyExpiryDate, condition, EquipmentStatus.AVAILABLE,
                                                                                            notes, engineHours, requiresCert, fuelType, dailyRate, deposit);
        equipmentRepository.save(heavyMachinery);
    }

    public void listEquipment(EquipmentStatus equipmentStatus) {
        List<Equipment> equipmentList = null;
        if(equipmentStatus != null) {
            equipmentList = equipmentRepository.findByUserIdAndStatus(SessionContext.getUser().getId(), equipmentStatus);
        }
        else
        {
            equipmentList = equipmentRepository.findByUserId(SessionContext.getUser().getId());
        }

        System.out.println("| EQUIPMENT |");
        Table table = Clique.table(TableType.BOX_DRAW)
                .headers(
                        "[*blue, bold]NAME[/]",
                        "[*blue, bold]BRAND[/]",
                        "[*blue, bold]MODEL[/]",
                        "[*blue, bold]TYPE[/]",
                        "[*blue, bold]CONDITION[/]",
                        "[*blue, bold]STATUS[/]",
                        "[*blue, bold]PURCHASE PRICE[/]"
                );
        equipmentList.forEach(equipment -> {
            table.row(equipment.getName(), equipment.getBrand(), equipment.getModel(), equipment.getEquipmentType().name(), equipment.getCondition().name(), equipment.getStatus().name(), InputHandler.formatAsMoney(equipment.getPrice()));
        });
        table.render();
    }

    public void addBookingNote(EventEquipment eventEquipment, LocalDate eventDate, String setupDetails, int headcount, String notes) {
        BookingNote bookingNote = new BookingNote(eventDate, setupDetails, headcount, notes);
        eventEquipment.getSetupLogs().add(bookingNote);

        equipmentRepository.save(eventEquipment);
    }

    public void retireEquipment(Equipment equipment) {
        if(EquipmentStatus.RENTED.equals(equipment.getStatus())) {
            System.out.println("You cannot retire this piece of equipment. It is rented!");
        }
        else
        {
            equipment.setStatus(EquipmentStatus.RETIRED);
            equipmentRepository.save(equipment);
            System.out.println("Equipment has been retired!");
        }
    }

    public void viewEquipmentDetails(Equipment equipment) {
        System.out.println(equipment.getSummary());
        //TODO: Add maintenance records
        //TODO: Add rental history
    }

    public void editPowerTool(PowerTool powerTool, EquipmentCondition condition, String notes, LocalDate warrantyExpiryDate, int voltage, double hourlyRate, double dailyRate) {
        powerTool.setCondition(condition);
        powerTool.setNotes(notes);
        powerTool.setWarrantyExpiry(warrantyExpiryDate);
        powerTool.setVoltage(voltage);
        powerTool.setHourlyRate(hourlyRate);
        powerTool.setDailyRate(dailyRate);

        equipmentRepository.save(powerTool);
    }


    public void editHeavyMachinery(HeavyMachinery heavyMachinery, EquipmentCondition condition, String notes,  LocalDate warrantyExpiryDate, double dailyRate, double deposit) {
        heavyMachinery.setCondition(condition);
        heavyMachinery.setNotes(notes);
        heavyMachinery.setWarrantyExpiry(warrantyExpiryDate);
        heavyMachinery.setDailyRate(dailyRate);
        heavyMachinery.setDeposit(deposit);

        equipmentRepository.save(heavyMachinery);
    }

    public void editEventEquipment(EventEquipment eventEquipment, EquipmentCondition condition, String notes, LocalDate warrantyExpiryDate, double flatEventRate) {
        eventEquipment.setCondition(condition);
        eventEquipment.setNotes(notes);
        eventEquipment.setWarrantyExpiry(warrantyExpiryDate);
        eventEquipment.setFlatEventRate(flatEventRate);

        equipmentRepository.save(eventEquipment);
    }
}
