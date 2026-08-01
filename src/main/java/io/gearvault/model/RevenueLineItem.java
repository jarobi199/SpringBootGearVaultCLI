package io.gearvault.model;

import io.gearvault.enums.EquipmentType;

public record RevenueLineItem(String equipmentName, EquipmentType equipmentType, double revenue) {}


