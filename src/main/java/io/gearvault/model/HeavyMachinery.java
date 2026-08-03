package io.gearvault.model;

import io.gearvault.enums.*;
import io.gearvault.interfaces.RentalRateStrategy;
import io.gearvault.pricing.TieredDailyRateStrategy;
import io.gearvault.util.InputHandler;
import org.springframework.data.annotation.Transient;

import java.time.LocalDate;

public class HeavyMachinery extends Equipment{
    private int engineHours;
    private boolean requiresOperatorCert;
    private FuelType fuelType;
    private double dailyRate;
    private double deposit;
    @Transient
    private RentalRateStrategy<HeavyMachinery> rateStrategy;

    public HeavyMachinery() {
        this.rateStrategy = new TieredDailyRateStrategy();
    }

    public HeavyMachinery(String userId, String name, String brand, String model, String serialNumber, double price, LocalDate purchaseDate, LocalDate warrantyExpiry,
                          EquipmentCondition condition, EquipmentStatus status, String notes, int engineHours,
                          boolean requiresOperatorCert, FuelType fuelType, double dailyRate, double deposit) {
        super(userId, name, brand, model, serialNumber, price, purchaseDate, warrantyExpiry, condition, status, notes);
        this.engineHours = engineHours;
        this.requiresOperatorCert = requiresOperatorCert;
        this.fuelType = fuelType;
        this.dailyRate = dailyRate;
        this.deposit = deposit;
    }

    public int getEngineHours() {
        return engineHours;
    }

    public void setEngineHours(int engineHours) {
        this.engineHours = engineHours;
    }

    public boolean isRequiresOperatorCert() {
        return requiresOperatorCert;
    }

    public void setRequiresOperatorCert(boolean requiresOperatorCert) {
        this.requiresOperatorCert = requiresOperatorCert;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    @Override
    public double computeRentalCost(int days) {
        return rateStrategy.computeCost(this, days);
    }

    @Override
    public EquipmentType getEquipmentType() {
        return EquipmentType.HEAVY_MACHINERY;
    }

    public String getCertWarning() {
        String certWarning = null;
        if (requiresOperatorCert) {
            certWarning = "⚠ Operator certification required for " + brand + " " + model + ". Verify customer credentials before checkout.";
        }

        return certWarning;
    }

    @Override
    public String getSummary() {
        return "| HEAVY MACHINERY - DETAILS |" +  "\n" +
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
                "Engine Hours: " + engineHours + "\n" +
                "Requires Operator Certification: " + requiresOperatorCert + "\n" +
                "Fuel Type: " + fuelType.name() + "\n" +
                "Deposit: "  +  InputHandler.formatAsMoney(deposit) + "\n" +
                "Daily Rate: " +  InputHandler.formatAsMoney(dailyRate);
    }

}
