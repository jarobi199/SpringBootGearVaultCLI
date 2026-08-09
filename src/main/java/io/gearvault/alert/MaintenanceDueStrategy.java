package io.gearvault.alert;

import io.gearvault.enums.AlertType;
import io.gearvault.interfaces.AlertStrategy;
import io.gearvault.model.Equipment;
import io.gearvault.model.HeavyMachinery;
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
        List<MaintenanceLog> maintenanceLogs = maintenanceLogRepository.findByEquipmentIdOrderByServiceDateDesc(equipment.getId());
        if((noLogsAndOverSixMonths(maintenanceLogs, equipment)) || logsAndRecentServiceSixMonths(maintenanceLogs) || checkHeavyMachineryAndHours(maintenanceLogs, equipment))  {
            alertResult = new AlertResult(equipment, AlertType.MAINTENANCE_DUE, "Maintenance required!");
        }

        return alertResult;
    }

    private boolean noLogsAndOverSixMonths(List<MaintenanceLog> maintenanceLogs, Equipment equipment) {
        return maintenanceLogs.isEmpty() && ChronoUnit.MONTHS.between(equipment.getPurchaseDate(), LocalDate.now()) > 6;
    }

    private boolean logsAndRecentServiceSixMonths(List<MaintenanceLog> maintenanceLogs) {
        boolean result = false;
        if(!maintenanceLogs.isEmpty()) {
            MaintenanceLog maintenanceLog = maintenanceLogs.getFirst();
            result = ChronoUnit.MONTHS.between(maintenanceLog.getServiceDate(), LocalDate.now()) > 6;
        }

        return result;
    }

    private boolean checkHeavyMachineryAndHours(List<MaintenanceLog> maintenanceLogs, Equipment equipment) {
        boolean result = false;
        if(equipment instanceof HeavyMachinery heavyMachinery) {
            if(!maintenanceLogs.isEmpty()) {
                MaintenanceLog maintenanceLog = maintenanceLogs.getFirst();
                if(heavyMachinery.getEngineHours()  - maintenanceLog.getHoursAtService() > 250) {
                    result = true;
                }
            }
        }

        return result;
    }

}
