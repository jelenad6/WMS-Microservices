package wms.orders_service.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import wms.orders_service.model.ShipmentRequest;


public interface ShipmentRequestRepository
        extends JpaRepository<ShipmentRequest, Long> {

    Optional<ShipmentRequest> findByShipmentRequestNumber(String shipmentRequestNumber);

    Optional<ShipmentRequest> findByOrderNumber(String orderNumber);
    
    List<ShipmentRequest> findByStatusAndPaymentDeadlineBefore(
            ShipmentRequest.ShipmentStatus status,
            LocalDateTime now);
}