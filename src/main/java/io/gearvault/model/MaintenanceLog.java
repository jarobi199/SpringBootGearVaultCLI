package io.gearvault.model;

import io.gearvault.enums.ServiceType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "maintenance_logs")
public class MaintenanceLog {
    @Id
    private String id;
    private String equipmentId;
    private LocalDate serviceDate;
    private ServiceType serviceType;
    private String description;
    private double cost;
    private int hoursAtService;
    private String technicianName;

    public MaintenanceLog() {
        //No argument constructor
    }

    public MaintenanceLog(LocalDate serviceDate, ServiceType serviceType, String description, double cost, int hoursAtService, String technicianName) {
        this.serviceDate = serviceDate;
        this.serviceType = serviceType;
        this.description = description;
        this.cost = cost;
        this.hoursAtService = hoursAtService;
        this.technicianName = technicianName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(String equipmentId) {
        this.equipmentId = equipmentId;
    }

    public LocalDate getServiceDate() {
        return serviceDate;
    }

    public void setServiceDate(LocalDate serviceDate) {
        this.serviceDate = serviceDate;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getHoursAtService() {
        return hoursAtService;
    }

    public void setHoursAtService(int hoursAtService) {
        this.hoursAtService = hoursAtService;
    }

    public String getTechnicianName() {
        return technicianName;
    }

    public void setTechnicianName(String technicianName) {
        this.technicianName = technicianName;
    }
}
