package wms.inventory_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import wms.inventory_service.dto.NewItemRequest;
import wms.inventory_service.model.Item;
import wms.inventory_service.model.Stock;
import wms.inventory_service.rabbitmq.InventoryEventPublisher;
import wms.inventory_service.rabbitmq.ItemCreatedEvent;
import wms.inventory_service.repository.ItemRepository;
import wms.inventory_service.repository.StockRepository;

@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final StockRepository stockRepository;
    private final InventoryEventPublisher inventoryEventPublisher;

    public ItemService(
            ItemRepository itemRepository,
            StockRepository stockRepository,
            InventoryEventPublisher inventoryEventPublisher) {

        this.itemRepository = itemRepository;
        this.stockRepository = stockRepository;
        this.inventoryEventPublisher = inventoryEventPublisher;
    }

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Item createItem(NewItemRequest request) {
        Item item = new Item();

        item.setItemCode(request.getItemCode());
        item.setName(request.getName());
        item.setUnitMeasure(Item.UnitMeasure.valueOf(request.getUnitMeasure()));
        item.setStatus(Item.ItemStatus.ACTIVE);

        Item savedItem = itemRepository.save(item);

        ItemCreatedEvent event = new ItemCreatedEvent(
                savedItem.getItemCode(),
                savedItem.getName(),
                savedItem.getUnitMeasure().name()
        );

        inventoryEventPublisher.publishItemCreated(event);

        return savedItem;
    }

    public List<Stock> getItemState(String itemCode) {
        return stockRepository.findByItemCode(itemCode);
    }
}