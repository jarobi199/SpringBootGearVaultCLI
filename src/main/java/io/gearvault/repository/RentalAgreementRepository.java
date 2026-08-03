package io.gearvault.repository;

import io.gearvault.enums.RentalStatus;
import io.gearvault.model.RentalAgreement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RentalAgreementRepository extends MongoRepository<RentalAgreement, String> {
    List<RentalAgreement> findByEquipmentId(String equipmentId);
    List<RentalAgreement> findByUserIdAndStatus(String userId, RentalStatus status);
}

