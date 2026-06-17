package wms.orders_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import wms.orders_service.model.CustomerOrder;

public interface CustomerOrderRepository
        extends JpaRepository<CustomerOrder, Long> {

    Optional<CustomerOrder> findByOrderNumber(String orderNumber);
}