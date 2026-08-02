package io.gearvault.model;

import io.gearvault.enums.*;
import io.gearvault.util.InputHandler;

import java.time.LocalDate;

public class PowerTool extends Equipment {
    private boolean batteryPowered;
    private int voltage;
    private ToolClass toolClass;
    private boolean requiresChargeCheck;
    private double hourlyRate;
    private double dailyRate;

    public PowerTool() {
        this.rateStrategy = null;
    }

    public PowerTool(String userId, String name, String brand, String model, String serialNumber, double price, LocalDate purchaseDate, LocalDate warrantyExpiry,
                     EquipmentCondition condition, EquipmentStatus status, String notes, boolean batteryPowered, int voltage, ToolClass toolClass, boolean requiresChargeCheck,
                     double hourlyRate, double dailyRate) {
        super(userId, name, brand, model, serialNumber, price, purchaseDate, warrantyExpiry, condition, status, notes);
        this.batteryPowered = batteryPowered;
        this.voltage = voltage;
        this.toolClass = toolClass;
        this.requiresChargeCheck = requiresChargeCheck;
        this.hourlyRate = hourlyRate;
        this.dailyRate = dailyRate;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(double hourlyRate) {
        this.hourlyRate = hourlyRate;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
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

    @Override
    public String getSummary() {
        return "| POWER TOOL - DETAILS |" +  "\n" +
                "Name: " + name + "\n" +
                "Brand: " + brand + "\n" +
                "Model: " + model + "\n" +
                "SerialNumber: " + serialNumber + "\n" +
                "Price: " +  InputHandler.formatAsMoney(price) + "\n" +
                "Purchase Date: " + purchaseDate + "\n" +
                "Warranty Expiry Date: " + warrantyExpiry + "\n" +
                "Condition: " + condition + "\n" +
                "Status: " + status.name() + "\n" +
                "Notes: " + notes + "\n" +
                "Voltage: " + voltage + "\n" +
                "Tool Class: " + toolClass.name() + "\n" +
                "Requires Charge Check: " + requiresChargeCheck + "\n" +
                "Hourly Rate: " + InputHandler.formatAsMoney(hourlyRate) + "\n" +
                "Daily Rate: " +  InputHandler.formatAsMoney(dailyRate);
    }

}
