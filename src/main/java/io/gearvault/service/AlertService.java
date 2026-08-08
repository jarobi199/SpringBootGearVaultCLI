package io.gearvault.service;

import io.gearvault.alert.AlertManager;
import io.gearvault.alert.AlertResult;
import io.gearvault.authentication.SessionContext;
import io.gearvault.enums.AlertType;
import io.gearvault.model.Equipment;
import io.gearvault.repository.EquipmentRepository;
import io.github.kusoroadeolu.clique.Clique;
import io.github.kusoroadeolu.clique.components.Table;
import io.github.kusoroadeolu.clique.configuration.TableType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {
    @Autowired
    private EquipmentRepository equipmentRepository;
    @Autowired
    private AlertManager alertManager;

    public void displayAlerts(AlertType alertType) {
        List<Equipment> vehicles = equipmentRepository.findByUserId(SessionContext.getUser().getId());
        List<AlertResult> results = alertManager.evaluate(vehicles);
        if(alertType != null) {
            results = results.stream().filter(result -> result.alertType().equals(alertType)).toList();
        }
        displayAlertResults(results);
    }

    private void displayAlertResults(List<AlertResult> alertResults) {
        if(alertResults.isEmpty()) {
            System.out.println("No alerts found.\n");
        }
        else
        {
            System.out.println("| ALERTS |");
            Table table = Clique.table(TableType.BOX_DRAW)
                    .headers(
                            "[*blue, bold]ALERT TYPE[/]",
                            "[*blue, bold]EQUIPMENT[/]",
                            "[*blue, bold]MESSAGE[/]"
                    );
            for (AlertResult alertResult : alertResults) {
                table.row(alertResult.alertType().getDisplayName(), alertResult.equipment().getBrand() + " " + alertResult.equipment().getModel(),  alertResult.message());
            }
            table.render();
        }
    }
}
