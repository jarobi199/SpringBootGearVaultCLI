package io.gearvault.model;

import io.gearvault.enums.RentalStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "rental_agreements")
public class RentalAgreement {
    @Id
    private String id;
    private String userId;
    private String equipmentId;
    private String customerName;
    private String customerPhone;
    private String equipmentName;
    private String equipmentSerialNumber;
    private String equipmentBrand;
    private String equipmentModel;
    private LocalDate checkOutDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    private double deposit;
    private RentalStatus status;
    private double totalCost;

    public  RentalAgreement() {
        //No argument constructor
    }

    public RentalAgreement(String userId, String equipmentId, String equipmentName, String equipmentSerialNumber, String equipmentBrand, String equipmentModel, String customerName,
                           String customerPhone, LocalDate checkOutDate, LocalDate dueDate, LocalDate returnDate, double deposit, RentalStatus status, double totalCost) {
        this.userId = userId;
        this.equipmentId = equipmentId;
        this.equipmentName = equipmentName;
        this.equipmentSerialNumber = equipmentSerialNumber;
        this.equipmentBrand = equipmentBrand;
        this.equipmentModel = equipmentModel;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
        this.checkOutDate = checkOutDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
        this.deposit = deposit;
        this.status = status;
        this.totalCost = totalCost;
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

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getEquipmentSerialNumber() {
        return equipmentSerialNumber;
    }

    public void setEquipmentSerialNumber(String equipmentSerialNumber) {
        this.equipmentSerialNumber = equipmentSerialNumber;
    }

    public String getEquipmentBrand() {
        return equipmentBrand;
    }

    public void setEquipmentBrand(String equipmentBrand) {
        this.equipmentBrand = equipmentBrand;
    }

    public String getEquipmentModel() {
        return equipmentModel;
    }

    public void setEquipmentModel(String equipmentModel) {
        this.equipmentModel = equipmentModel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public RentalStatus getStatus() {
        return status;
    }

    public void setStatus(RentalStatus status) {
        this.status = status;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }
}

