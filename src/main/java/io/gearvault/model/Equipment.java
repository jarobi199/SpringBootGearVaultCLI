package io.gearvault.model;

import io.gearvault.enums.EquipmentCondition;
import io.gearvault.enums.EquipmentType;
import io.gearvault.enums.RentalStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "users")
public abstract class Equipment {
    @Id
    protected String id;
    protected String userId;
    protected String brand;
    protected String model;
    protected String serialNumber;
    protected double price;
    protected LocalDate purchaseDate;
    protected LocalDate warrantyExpiry;
    protected EquipmentCondition condition;
    protected RentalStatus status;
    protected String notes;

    public Equipment() {
        //No argument constructor
    }

    public Equipment(String userId, String brand, String model, String serialNumber, double price, LocalDate purchaseDate,
                     LocalDate warrantyExpiry, EquipmentCondition condition, RentalStatus status, String notes) {
        this.userId = userId;
        this.brand = brand;
        this.model = model;
        this.serialNumber = serialNumber;
        this.price = price;
        this.purchaseDate = purchaseDate;
        this.warrantyExpiry = warrantyExpiry;
        this.condition = condition;
        this.status = status;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getWarrantyExpiry() {
        return warrantyExpiry;
    }

    public void setWarrantyExpiry(LocalDate warrantyExpiry) {
        this.warrantyExpiry = warrantyExpiry;
    }

    public EquipmentCondition getCondition() {
        return condition;
    }

    public void setCondition(EquipmentCondition condition) {
        this.condition = condition;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public abstract double computeRentalCost(int days);

    public abstract EquipmentType getEquipmentType();

}

