package io.gearvault.service;

import io.gearvault.authentication.SessionContext;
import io.gearvault.enums.EquipmentStatus;
import io.gearvault.enums.ServiceType;
import io.gearvault.model.Equipment;
import io.gearvault.model.MaintenanceLog;
import io.gearvault.repository.EquipmentRepository;
import io.gearvault.repository.MaintenanceLogRepository;
import io.gearvault.util.InputHandler;
import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class MaintenanceLogService {
    @Autowired
    private MaintenanceLogRepository maintenanceLogRepository;
    @Autowired
    private EquipmentRepository equipmentRepository;

    public void addMaintenanceLog(Equipment equipment, LocalDate serviceDate, ServiceType serviceType, String description, double cost, int hours, String name) {
        MaintenanceLog maintenanceLog = new MaintenanceLog(SessionContext.getUser().getId(), equipment.getId(), serviceDate, serviceType, description, cost, hours, name);
        maintenanceLogRepository.save(maintenanceLog);

        equipment.setStatus(EquipmentStatus.IN_MAINTENANCE);
        equipmentRepository.save(equipment);
    }

    public void viewHistory(Equipment equipment) {
        List<MaintenanceLog> maintenanceLogs = maintenanceLogRepository.findByEquipmentIdOrderByServiceDateDesc(equipment.getId());
        double totalCost = 0;
        System.out.println("| MAINTENANCE LOGS |");
        Table table = Clique.table(TableType.BOX_DRAW)
                .headers(
                        "[*blue, bold]SERVICE TYPE[/]",
                        "[*blue, bold]DATE[/]",
                        "[*blue, bold]DESCRIPTION[/]",
                        "[*blue, bold]COST[/]",
                        "[*blue, bold]HOURS AT SERVICE[/]",
                        "[*blue, bold]TECHNICIAN[/]"
                );
        for (MaintenanceLog maintenanceLog : maintenanceLogs) {
            table.row(maintenanceLog.getServiceType().name(), maintenanceLog.getServiceDate().toString(), maintenanceLog.getDescription(), InputHandler.formatAsMoney(maintenanceLog.getCost()), String.valueOf(maintenanceLog.getHoursAtService()), maintenanceLog.getTechnicianName());
            totalCost = totalCost + maintenanceLog.getCost();
        }
        table.render();
        System.out.println("TOTAL MAINTENANCE COST: " + InputHandler.formatAsMoney(totalCost) );
    }

    public List<MaintenanceLog> findMaintenanceLogsByEquipmentId(Equipment equipment) {
        return maintenanceLogRepository.findByEquipmentId(equipment.getId());
    }

    public void deleteMaintenanceLog(MaintenanceLog maintenanceLog) {
        maintenanceLogRepository.delete(maintenanceLog);
    }

    public void viewAllMaintenanceRecords() {
        List<MaintenanceLog> maintenanceLogList = maintenanceLogRepository.findByUserIdOrderByServiceDateDesc(SessionContext.getUser().getId());

        if(!maintenanceLogList.isEmpty()){
            System.out.println("| ALL MAINTENANCE LOGS |");
            Table table = Clique.table(TableType.BOX_DRAW)
                    .headers(
                            "[*blue, bold]DATE[/]",
                            "[*blue, bold]EQUIPMENT NAME[/]",
                            "[*blue, bold]EQUIPMENT SERIAL NUMBER[/]",
                            "[*blue, bold]EQUIPMENT BRAND[/]",
                            "[*blue, bold]EQUIPMENT MODEL[/]",
                            "[*blue, bold]SERVICE TYPE[/]",
                            "[*blue, bold]DESCRIPTION[/]",
                            "[*blue, bold]COST[/]",
                            "[*blue, bold]HOURS AT SERVICE[/]",
                            "[*blue, bold]TECHNICIAN[/]"
                    );
            for(MaintenanceLog maintenanceLog : maintenanceLogList) {
                Equipment equipment = equipmentRepository.findById(maintenanceLog.getEquipmentId()).get();
                table.row(maintenanceLog.getServiceDate().toString(), equipment.getName(), equipment.getSerialNumber(), equipment.getBrand(), equipment.getModel(), maintenanceLog.getServiceType().name(),
                        maintenanceLog.getDescription(), InputHandler.formatAsMoney(maintenanceLog.getCost()), String.valueOf(maintenanceLog.getHoursAtService()), maintenanceLog.getTechnicianName());
            }
            table.render();
        }
    }

}
