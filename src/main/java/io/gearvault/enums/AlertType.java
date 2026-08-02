package io.gearvault.enums;

public enum AlertType {

    OVERDUE_RETURN("Overdue Return"),
    MAINTENANCE_DUE("Maintenance Due"),
    WARRANTY_EXPIRING("Warranty Expiring");

    private final String displayName;

    AlertType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}