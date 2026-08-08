package io.gearvault.alert;

import io.gearvault.interfaces.AlertStrategy;
import io.gearvault.model.Equipment;
import org.springframework.stereotype.Component;

@Component
public class OverdueReturnStrategy implements AlertStrategy {
    @Override
    public boolean supports(Equipment equipment) {
        return false;
    }

    @Override
    public AlertResult evaluate(Equipment equipment) {
        return null;
    }
}
