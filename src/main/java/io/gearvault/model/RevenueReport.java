package io.gearvault.model;

import io.gearvault.enums.EquipmentType;

import java.util.List;
import java.util.Map;

public record RevenueReport(double totalRevenue, Map<EquipmentType, Double> revenueByType, double depositOutstanding, List<RevenueLineItem> topEarningItems ) {}
