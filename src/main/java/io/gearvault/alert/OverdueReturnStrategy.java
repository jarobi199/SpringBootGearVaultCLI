package io.gearvault.alert;

import io.gearvault.authentication.SessionContext;
import io.gearvault.enums.AlertType;
import io.gearvault.enums.RentalStatus;
import io.gearvault.interfaces.AlertStrategy;
import io.gearvault.model.Equipment;
import io.gearvault.model.RentalAgreement;
import io.gearvault.repository.RentalAgreementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class OverdueReturnStrategy implements AlertStrategy {

    @Autowired
    private RentalAgreementRepository rentalAgreementRepository;
    private List<RentalAgreement> rentalAgreements;

    @Override
    public boolean supports(Equipment equipment) {
        boolean result = false;
        if(equipment != null) {
            rentalAgreements = rentalAgreementRepository.findByEquipmentIdAndStatus(equipment.getId(), RentalStatus.ACTIVE);
            if(!rentalAgreements.isEmpty()) {
                result = true;
            }
        }
        return result;
    }

    @Override
    public AlertResult evaluate(Equipment equipment) {
        AlertResult alertResult = null;
        if(!rentalAgreements.isEmpty()) {
            List<RentalAgreement> overdueRentals = rentalAgreements
                    .stream()
                    .filter(rentalAgreement -> rentalAgreement.getDueDate().isBefore(LocalDate.now().plusDays(SessionContext.getUser().getOverdueGraceDays()))).toList();
            if(!overdueRentals.isEmpty()) {
                alertResult = new AlertResult(equipment, AlertType.OVERDUE_RETURN, "This piece of equipment is overdue!");
            }
        }

        return alertResult;
    }
}

