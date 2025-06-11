package lt.viko.eif.nSalunov.DB.repository;

import lt.viko.eif.nSalunov.DB.model.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    // Jei prireiks custom metodų (pagal laukus), jie rašomi čia kaip:
    // List<OrderEntity> findByUserId(Long userId);
}
