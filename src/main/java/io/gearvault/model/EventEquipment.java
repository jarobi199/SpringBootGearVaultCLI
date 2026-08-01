package io.gearvault.model;

import io.gearvault.enums.EquipmentCondition;
import io.gearvault.enums.EquipmentType;
import io.gearvault.enums.EventCategory;
import io.gearvault.enums.RentalStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EventEquipment extends Equipment {
    private EventCategory eventCategory;
    private int quantity;
    private boolean setupRequired;
    private double flatEventRate;
    private List<BookingNote> setupLogs;

    public EventEquipment() {
        this.rateStrategy = null;
        this.setupLogs = new ArrayList<>();
    }

    public EventEquipment(String userId, String name, String brand, String model, String serialNumber, double price, LocalDate purchaseDate, LocalDate warrantyExpiry, EquipmentCondition condition, RentalStatus status,
                          String notes, EventCategory eventCategory, int quantity, boolean setupRequired, double flatEventRate) {
        super(userId, name, brand, model, serialNumber, price, purchaseDate, warrantyExpiry, condition, status, notes);
        this.eventCategory = eventCategory;
        this.quantity = quantity;
        this.setupRequired = setupRequired;
        this.flatEventRate = flatEventRate;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isSetupRequired() {
        return setupRequired;
    }

    public void setSetupRequired(boolean setupRequired) {
        this.setupRequired = setupRequired;
    }

    public double getFlatEventRate() {
        return flatEventRate;
    }

    public void setFlatEventRate(double flatEventRate) {
        this.flatEventRate = flatEventRate;
    }

    public List<BookingNote> getSetupLogs() {
        return setupLogs;
    }

    public void setSetupLogs(List<BookingNote> setupLogs) {
        this.setupLogs = setupLogs;
    }

    @Override
    public double computeRentalCost(int days) {
        return 0;
    }

    @Override
    public EquipmentType getEquipmentType() {
        return EquipmentType.EVENT_EQUIPMENT;
    }
}
