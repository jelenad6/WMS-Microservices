package wms.orders_service.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import wms.orders_service.service.CatalogService;

@Component
public class GoodsReplenishedListener {

    private final CatalogService catalogService;

    public GoodsReplenishedListener(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @RabbitListener(queues = "inventory.goods.replenished.queue")
    public void handleGoodsReplenished(GoodsReplenishedEvent event) {

        System.out.println("ORDERS RECEIVED GOODS REPLENISHED: "
                + event.getItemCode()
                + " price=" + event.getPurchasePrice());

        catalogService.updatePurchasePrice(event);
    }
}