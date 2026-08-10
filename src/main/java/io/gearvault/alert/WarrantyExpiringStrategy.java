package io.gearvault.alert;

import io.gearvault.enums.AlertType;
import io.gearvault.interfaces.AlertStrategy;
import io.gearvault.model.Equipment;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class WarrantyExpiringStrategy implements AlertStrategy {
    @Override
    public boolean supports(Equipment equipment) {
        return (equipment != null) && (equipment.getWarrantyExpiry() != null);
    }

    @Override
    public AlertResult evaluate(Equipment equipment) {
        AlertResult alertResult = null;
        long days = ChronoUnit.DAYS.between(LocalDate.now(), equipment.getWarrantyExpiry());
        if (days > 0 && days <= 30) {
            alertResult = new AlertResult(equipment, AlertType.WARRANTY_EXPIRING, "This warranty is about to expiry in " + days + " days!");
        }

        return alertResult;
    }
}
