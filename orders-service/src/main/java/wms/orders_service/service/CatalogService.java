package wms.orders_service.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import wms.orders_service.model.CatalogItem;
import wms.orders_service.rabbitmq.GoodsReplenishedEvent;
import wms.orders_service.rabbitmq.ItemCreatedEvent;
import wms.orders_service.repository.CatalogItemRepository;

@Service
public class CatalogService {
	
	private final CatalogItemRepository catalogItemRepository;

	public CatalogService(CatalogItemRepository catalogItemRepository) {
	    this.catalogItemRepository = catalogItemRepository;
	}
	
	public void saveItem(ItemCreatedEvent event) {

	    if (catalogItemRepository
	            .findByItemCode(event.getItemCode())
	            .isPresent()) {
	        return;
	    }

	    CatalogItem item = new CatalogItem();

	    item.setItemCode(event.getItemCode());
	    item.setName(event.getName());
	    item.setUnitMeasure(event.getUnitMeasure());
	    
	    item.setAveragePurchasePrice(BigDecimal.ZERO);

	    item.setMarginPercentage(
	            new BigDecimal("15"));

	    item.setSellingPrice(BigDecimal.ZERO);

	    catalogItemRepository.save(item);
	}
	
	public void updatePurchasePrice(
	        GoodsReplenishedEvent event) {

	    CatalogItem item = catalogItemRepository
	            .findByItemCode(event.getItemCode())
	            .orElseThrow();

	    item.setAveragePurchasePrice(
	            event.getPurchasePrice());

	    BigDecimal sellingPrice =
	            event.getPurchasePrice()
	                    .multiply(new BigDecimal("1.15"));

	    item.setSellingPrice(sellingPrice);

	    catalogItemRepository.save(item);
	}

}
