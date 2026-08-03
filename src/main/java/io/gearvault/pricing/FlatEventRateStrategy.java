package io.gearvault.pricing;

import io.gearvault.interfaces.RentalRateStrategy;
import io.gearvault.model.EventEquipment;

public class FlatEventRateStrategy implements RentalRateStrategy<EventEquipment> {
    @Override
    public double computeCost(EventEquipment equipment, int days) {
        return equipment.getFlatEventRate() * equipment.getQuantity();
    }
}

