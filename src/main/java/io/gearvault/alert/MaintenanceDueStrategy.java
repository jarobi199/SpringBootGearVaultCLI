package io.gearvault.alert;

import io.gearvault.interfaces.AlertStrategy;
import io.gearvault.model.Equipment;
import org.springframework.stereotype.Component;

@Component
public class MaintenanceDueStrategy implements AlertStrategy {
    @Override
    public boolean supports(Equipment equipment) {
        return (equipment != null);
    }

    @Override
    public AlertResult evaluate(Equipment equipment) {
        return null;
    }
}
