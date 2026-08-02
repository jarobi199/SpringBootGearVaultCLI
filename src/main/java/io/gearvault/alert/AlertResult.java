package io.gearvault.alert;

import io.gearvault.enums.AlertType;
import io.gearvault.model.Equipment;

public record AlertResult (Equipment equipment, AlertType alertType, String message) {}