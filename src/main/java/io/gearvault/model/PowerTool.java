package io.gearvault.model;

import io.gearvault.enums.EquipmentCondition;
import io.gearvault.enums.EquipmentType;
import io.gearvault.enums.RentalStatus;
import io.gearvault.enums.ToolClass;

import java.time.LocalDate;

public class PowerTool extends Equipment {
    private boolean batteryPowered;
    private int voltage;
    private ToolClass toolClass;
    private boolean requiresChargeCheck;

    public PowerTool() {
        this.rateStrategy = null;
    }

    public PowerTool(String userId, String brand, String model, String serialNumber, double price, LocalDate purchaseDate,
                     LocalDate warrantyExpiry, EquipmentCondition condition, RentalStatus status, String notes, boolean batteryPowered, int voltage,
                     ToolClass toolClass, boolean requiresChargeCheck) {
        super(userId, brand, model, serialNumber, price, purchaseDate, warrantyExpiry, condition, status, notes);
        this.batteryPowered = batteryPowered;
        this.voltage = voltage;
        this.toolClass = toolClass;
        this.requiresChargeCheck = requiresChargeCheck;
    }

    public boolean isBatteryPowered() {
        return batteryPowered;
    }

    public void setBatteryPowered(boolean batteryPowered) {
        this.batteryPowered = batteryPowered;
    }

    public int getVoltage() {
        return voltage;
    }

    public void setVoltage(int voltage) {
        this.voltage = voltage;
    }

    public ToolClass getToolClass() {
        return toolClass;
    }

    public void setToolClass(ToolClass toolClass) {
        this.toolClass = toolClass;
    }

    public boolean isRequiresChargeCheck() {
        return requiresChargeCheck;
    }

    public void setRequiresChargeCheck(boolean requiresChargeCheck) {
        this.requiresChargeCheck = requiresChargeCheck;
    }

    @Override
    public double computeRentalCost(int days) {
        return 0;
    }

    @Override
    public EquipmentType getEquipmentType() {
        return EquipmentType.POWER_TOOL;
    }
}
