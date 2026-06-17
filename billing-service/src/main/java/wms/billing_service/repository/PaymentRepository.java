package wms.billing_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import wms.billing_service.model.Payment;

public interface PaymentRepository
        extends JpaRepository<Payment, Long> {

    Optional<Payment> findByShipmentRequestNumber(
            String shipmentRequestNumber);
}