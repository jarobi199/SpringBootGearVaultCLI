package io.gearvault.interfaces;

import io.gearvault.model.Equipment;

public interface RentalRateStrategy<T extends Equipment> {
    double computeCost(T equipment, int days);
}
