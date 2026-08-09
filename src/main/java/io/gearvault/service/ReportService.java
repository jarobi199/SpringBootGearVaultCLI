package io.gearvault.service;

import io.gearvault.repository.EquipmentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    @Autowired
    private EquipmentRepository equipmentRepository;

    public void createRevenueReport() {

    }
}
