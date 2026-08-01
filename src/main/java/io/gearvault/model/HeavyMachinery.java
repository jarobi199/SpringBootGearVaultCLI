package io.gearvault.model;

import io.gearvault.enums.EquipmentCondition;
import io.gearvault.enums.EquipmentType;
import io.gearvault.enums.FuelType;
import io.gearvault.enums.RentalStatus;

import java.time.LocalDate;

public class HeavyMachinery extends Equipment{
    private int engineHours;
    private boolean requiresOperatorCert;
    private FuelType fuelType;
    private double dailyRate;
    private double deposit;

    public HeavyMachinery() {
        this.rateStrategy = null;
    }

    public HeavyMachinery(String userId,  String name, String brand, String model, String serialNumber, double price, LocalDate purchaseDate, LocalDate warrantyExpiry,
                          EquipmentCondition condition, RentalStatus status, String notes, int engineHours,
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
        return 0;
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

}
