package wms.inventory_service.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import wms.inventory_service.dto.ReceptionItemRequest;
import wms.inventory_service.dto.ReceptionRequest;
import wms.inventory_service.model.Stock;
import wms.inventory_service.repository.StockRepository;
import wms.inventory_service.rabbitmq.GoodsReplenishedEvent;
import wms.inventory_service.rabbitmq.InventoryEventPublisher;

@Service
public class ReceptionService {

    private final StockRepository stockRepository;
    private final InventoryEventPublisher inventoryEventPublisher;

    public ReceptionService(
            StockRepository stockRepository,
            InventoryEventPublisher inventoryEventPublisher) {
        this.stockRepository = stockRepository;
        this.inventoryEventPublisher = inventoryEventPublisher;
    }
    

    @Transactional
    public void receiveGoods(ReceptionRequest request) {

        for (ReceptionItemRequest item : request.getItems()) {

            Stock stock = stockRepository
                    .findByWarehouseCodeAndItemCode(
                            request.getWarehouseCode(),
                            item.getItemCode())
                    .orElse(null);

            if (stock == null) {
                stock = new Stock();

                stock.setWarehouseCode(request.getWarehouseCode());
                stock.setItemCode(item.getItemCode());
                stock.setTotalQuantity(item.getQuantity());
                stock.setReservedQuantity(BigDecimal.ZERO);
                stock.setAvailableQuantity(item.getQuantity());
            } else {
                stock.setTotalQuantity(
                        stock.getTotalQuantity().add(item.getQuantity())
                );

                stock.setAvailableQuantity(
                        stock.getAvailableQuantity().add(item.getQuantity())
                );
            }

            stockRepository.save(stock);
            
            GoodsReplenishedEvent event = new GoodsReplenishedEvent(
                    request.getWarehouseCode(),
                    item.getItemCode(),
                    item.getQuantity(),
                    item.getPurchasePrice()
            );

            inventoryEventPublisher.publishGoodsReplenished(event);
        }
    }
}