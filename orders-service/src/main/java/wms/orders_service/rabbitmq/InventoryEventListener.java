package wms.orders_service.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


import wms.orders_service.service.CatalogService;

@Component
public class InventoryEventListener {

    private final CatalogService catalogService;

    public InventoryEventListener(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @RabbitListener(queues = "inventory.item.created.queue")
    public void handleItemCreated(ItemCreatedEvent event) {

        System.out.println("ORDERS RECEIVED ITEM CREATED: "
                + event.getItemCode() + " - " + event.getName());

        catalogService.saveItem(event);
    }
}