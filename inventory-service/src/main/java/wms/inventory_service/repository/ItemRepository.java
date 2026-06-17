package wms.inventory_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wms.inventory_service.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {

}