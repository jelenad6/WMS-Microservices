package wms.orders_service.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrdersEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public OrdersEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishReserveStock(ReserveStockEvent event) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDERS_EXCHANGE,
                RabbitMQConfig.RESERVE_STOCK_ROUTING_KEY,
                event
        );
    }
    
    public void publishGoodsSold(
            GoodsSoldEvent event) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDERS_EXCHANGE,
                RabbitMQConfig.GOODS_SOLD_ROUTING_KEY,
                event
        );
    }
    
    public void publishCancelReservation(
            CancelReservationEvent event) {

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.ORDERS_EXCHANGE,
                RabbitMQConfig.CANCEL_RESERVATION_ROUTING_KEY,
                event
        );
    }
}