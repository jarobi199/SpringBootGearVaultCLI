package io.gearvault.repository;

import io.gearvault.model.MaintenanceLog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceLogRepository extends MongoRepository<MaintenanceLog, String> {
    List<MaintenanceLog> findByEquipmentId (String equipmentId );
    List<MaintenanceLog> findByEquipmentIdOrderByServiceDateDesc(String equipmentId);
    List<MaintenanceLog>  findByUserIdOrderByServiceDateDesc(String userId);
}
