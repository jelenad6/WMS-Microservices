package wms.inventory_service.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class InventoryEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public InventoryEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishItemCreated(ItemCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.INVENTORY_EXCHANGE,
                RabbitMQConfig.ITEM_CREATED_ROUTING_KEY,
                event
        );
    }
    public void publishGoodsReplenished(GoodsReplenishedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.INVENTORY_EXCHANGE,
                RabbitMQConfig.GOODS_REPLENISHED_ROUTING_KEY,
                event
        );
    }
    
    public void publishStockReserved(StockReservedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.INVENTORY_EXCHANGE,
                RabbitMQConfig.STOCK_RESERVED_ROUTING_KEY,
                event
        );
    }

    public void publishStockReservationFailed(StockReservationFailedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.INVENTORY_EXCHANGE,
                RabbitMQConfig.STOCK_RESERVATION_FAILED_ROUTING_KEY,
                event
        );
    }
}