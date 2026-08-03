package io.gearvault.pricing;

import io.gearvault.interfaces.RentalRateStrategy;
import io.gearvault.model.PowerTool;

public class HourlyCappedRateStrategy implements RentalRateStrategy<PowerTool> {
    @Override
    public double computeCost(PowerTool equipment, int days) {
        return Math.min(equipment.getHourlyRate() * 8, equipment.getDailyRate()) * days;
    }
}

