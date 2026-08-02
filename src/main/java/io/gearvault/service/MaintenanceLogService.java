package io.gearvault.service;

import io.gearvault.enums.EquipmentStatus;
import io.gearvault.enums.ServiceType;
import io.gearvault.model.Equipment;
import io.gearvault.model.MaintenanceLog;
import io.gearvault.repository.EquipmentRepository;
import io.gearvault.repository.MaintenanceLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MaintenanceLogService {
    @Autowired
    private MaintenanceLogRepository maintenanceLogRepository;
    @Autowired
    private EquipmentRepository equipmentRepository;

    public void addMaintenanceLog(Equipment equipment, LocalDate serviceDate, ServiceType serviceType, String description, double cost, int hours, String name) {
        MaintenanceLog maintenanceLog = new MaintenanceLog(serviceDate, serviceType, description, cost, hours, name);
        maintenanceLogRepository.save(maintenanceLog);

        equipment.setStatus(EquipmentStatus.IN_MAINTENANCE);
        equipmentRepository.save(equipment);
    }
}
