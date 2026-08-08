package io.gearvault.alert;

import io.gearvault.interfaces.AlertStrategy;
import io.gearvault.model.Equipment;
import io.gearvault.model.MaintenanceLog;
import io.gearvault.repository.MaintenanceLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class MaintenanceDueStrategy implements AlertStrategy {

    @Autowired
    private MaintenanceLogRepository maintenanceLogRepository;

    @Override
    public boolean supports(Equipment equipment) {
        return (equipment != null);
    }

    @Override
    public AlertResult evaluate(Equipment equipment) {
        AlertResult alertResult = null;
        List<MaintenanceLog> maintenanceLogs = maintenanceLogRepository.findByEquipmentId(equipment.getId());
        if(maintenanceLogs.isEmpty() && ChronoUnit.MONTHS.between(equipment.getPurchaseDate(), LocalDate.now()) > 6) {

        }

        return alertResult;
    }
}
