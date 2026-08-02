package io.gearvault.interfaces;

import io.gearvault.alert.AlertResult;
import io.gearvault.model.Equipment;

public interface AlertStrategy {
    boolean supports(Equipment equipment);
    AlertResult evaluate(Equipment equipment);
}
