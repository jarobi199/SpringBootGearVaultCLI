package io.gearvault.service;

import io.gearvault.authentication.SessionContext;
import io.gearvault.enums.EquipmentStatus;
import io.gearvault.enums.EquipmentType;
import io.gearvault.enums.RentalStatus;
import io.gearvault.model.Equipment;
import io.gearvault.model.RentalAgreement;
import io.gearvault.model.RevenueLineItem;
import io.gearvault.model.RevenueReport;
import io.gearvault.repository.EquipmentRepository;

import io.gearvault.repository.RentalAgreementRepository;
import io.gearvault.util.BarChartUtil;
import io.gearvault.util.InputHandler;
import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportService {
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private RentalAgreementRepository rentalAgreementRepository;

    public void createRevenueReport() {
        List<RentalAgreement> rentalAgreements = rentalAgreementRepository.findByUserId(SessionContext.getUser().getId());

        //Total Revenue
        double totalRevenue = rentalAgreements.stream().mapToDouble(RentalAgreement::getTotalCost).sum();

        //Revenue By Type
        Map<EquipmentType, Double> revenueByType = new HashMap<>();
        for(EquipmentType equipmentType : EquipmentType.values()) {
            revenueByType.put(equipmentType, 0.0);
        }
        rentalAgreements.forEach(rentalAgreement -> {
            Optional<Equipment> optionalEquipment = equipmentRepository.findById(rentalAgreement.getEquipmentId());
            optionalEquipment.ifPresent(equipment -> {
                revenueByType.replace(equipment.getEquipmentType(), revenueByType.get(equipment.getEquipmentType()) + rentalAgreement.getTotalCost());
           });
        });

        //Deposit Outstanding
        double totalDeposits = rentalAgreements.stream().filter(rentalAgreement -> RentalStatus.ACTIVE.equals(rentalAgreement.getStatus())).mapToDouble(RentalAgreement::getDeposit).sum();
        double depositsOutstanding =  (totalDeposits - totalRevenue) >= 0 ? totalDeposits - totalRevenue : 0.0;

        //Top Earnings
        List<RevenueLineItem> topEarningItems = new ArrayList<>();
        List<Equipment> equipmentList = equipmentRepository.findByUserId(SessionContext.getUser().getId());
        for(Equipment equipment : equipmentList) {
            double revenue = rentalAgreements.stream().filter(rentalAgreement -> rentalAgreement.getEquipmentId().equals(equipment.getId())).mapToDouble(RentalAgreement::getTotalCost).sum();
            topEarningItems.add(new RevenueLineItem(equipment.getName(), equipment.getEquipmentType(), revenue));
        }

        //Display Revenue Report
        RevenueReport revenueReport = new RevenueReport(totalRevenue, revenueByType, depositsOutstanding, topEarningItems);

        System.out.println("| REVENUE REPORT |");
        Table revenueReportTable = Clique.table(TableType.BOX_DRAW)
                .headers(
                        "[*blue, bold]TOTAL REVENUE[/]",
                        "[*blue, bold]TOTAL OUTSTANDING DEPOSITS (ACTIVE RENTALS)[/]"
                );
        revenueReportTable.row(InputHandler.formatAsMoney(revenueReport.totalRevenue()), InputHandler.formatAsMoney(revenueReport.depositOutstanding()));
        revenueReportTable.render();

        //Display Barchart
        BarChartUtil.Builder barChartTable = BarChartUtil.builder().title("Revenue By Equipment Type");
        for (Map.Entry<EquipmentType, Double> entry : revenueByType.entrySet()) {
            barChartTable.bar(entry.getKey().name(), entry.getValue());
        }
        barChartTable.maxWidth(40).render();

        System.out.println("| REVENUE BY EQUIPMENT |");
        Table revenueByEquipmentTable = Clique.table(TableType.BOX_DRAW)
                .headers(
                        "[*blue, bold]EQUIPMENT NAME[/]",
                        "[*blue, bold]EQUIPMENT TYPE[/]",
                        "[*blue, bold]REVENUE[/]"
                );
        for(RevenueLineItem revenueLineItem : revenueReport.topEarningItems()) {
            revenueByEquipmentTable.row(revenueLineItem.equipmentName(), revenueLineItem.equipmentType().name(),  InputHandler.formatAsMoney(revenueLineItem.revenue()));
        }
        revenueByEquipmentTable.render();

    }

    public void createUtilizationReport() {
        List<Equipment> equipmentList = equipmentRepository.findByUserId(SessionContext.getUser().getId());
        int totalEquipment = equipmentList.size();
        long totalRented = equipmentList.stream().filter(equipment -> EquipmentStatus.RENTED.equals(equipment.getStatus())).count();
        long totalAvailable = equipmentList.stream().filter(equipment -> EquipmentStatus.AVAILABLE.equals(equipment.getStatus())).count();
        long utilizationRatePercent = (totalRented * 100) / totalEquipment;

        //Utilization Rate By Equipment Type
        Map<EquipmentType, Long> utilizationByTypeMap = new HashMap<>();
        for(EquipmentType equipmentType : EquipmentType.values()) {
            long value = (equipmentList.stream().filter(equipment -> EquipmentStatus.RENTED.equals(equipment.getStatus()) && (equipment.getEquipmentType().equals(equipmentType))).count() * 100) / totalEquipment;
            utilizationByTypeMap.put(equipmentType, value);
        }

        System.out.println("| UTILIZATION REPORT |");
        Table utilizationReportTable = Clique.table(TableType.BOX_DRAW)
                .headers(
                        "[*blue, bold]TOTAL EQUIPMENT COUNT[/]",
                        "[*blue, bold]EQUIPMENT AVAILABLE[/]",
                        "[*blue, bold]EQUIPMENT RENTED[/]",
                        "[*blue, bold]UTILIZATION RATE[/]"
                );
        utilizationReportTable.row(String.valueOf(totalEquipment), String.valueOf(totalAvailable),  String.valueOf(totalRented), utilizationRatePercent + "%");
        utilizationReportTable.render();

        System.out.println();
        System.out.println("| UTILIZATION RATE BY EQUIPMENT TYPE |");
        Table utilizationRateByEquipmentTypeTable = Clique.table(TableType.BOX_DRAW)
                .headers(
                        "[*blue, bold]EQUIPMENT TYPE[/]",
                        "[*blue, bold]UTILIZATION RATE[/]"
                );
        for(Map.Entry<EquipmentType, Long> entry : utilizationByTypeMap.entrySet()) {
            utilizationRateByEquipmentTypeTable.row(entry.getKey().name(), entry.getValue() + "%");
        }
        utilizationRateByEquipmentTypeTable.render();
    }

}
