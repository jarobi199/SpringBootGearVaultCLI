package io.gearvault.interfaces;

import io.gearvault.alert.AlertResult;
import io.gearvault.model.Equipment;

public interface AlertStrategy {

    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_RESET = "\u001B[0m";

    boolean supports(Equipment equipment);
    AlertResult evaluate(Equipment equipment);
}
