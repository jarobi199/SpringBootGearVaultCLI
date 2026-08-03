package io.gearvault.pricing;

import io.gearvault.interfaces.RentalRateStrategy;
import io.gearvault.model.HeavyMachinery;

public class TieredDailyRateStrategy implements RentalRateStrategy<HeavyMachinery> {
    @Override
    public double computeCost(HeavyMachinery equipment, int days) {
        return equipment.getDailyRate()  + ((days -1) * 0.80 * equipment.getDailyRate()) + equipment.getDeposit();
    }
}

