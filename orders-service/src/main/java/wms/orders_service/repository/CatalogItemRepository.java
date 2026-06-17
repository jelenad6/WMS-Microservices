package wms.orders_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import wms.orders_service.model.CatalogItem;

public interface CatalogItemRepository
        extends JpaRepository<CatalogItem, Long> {

    Optional<CatalogItem> findByItemCode(String itemCode);
}