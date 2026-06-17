package wms.orders_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import wms.orders_service.model.OrderItem;

public interface OrderItemRepository
        extends JpaRepository<OrderItem, Long> {

}