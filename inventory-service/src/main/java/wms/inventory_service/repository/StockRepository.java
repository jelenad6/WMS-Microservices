package wms.inventory_service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import wms.inventory_service.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {

    List<Stock> findByItemCode(String itemCode);
    
    Optional<Stock> findByWarehouseCodeAndItemCode(
            String warehouseCode,
            String itemCode);
}

