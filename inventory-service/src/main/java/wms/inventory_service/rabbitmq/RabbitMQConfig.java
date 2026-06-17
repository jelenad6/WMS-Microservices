package wms.inventory_service.rabbitmq;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class RabbitMQConfig {

    public static final String INVENTORY_EXCHANGE = "inventory.events.exchange";

    public static final String ITEM_CREATED_ROUTING_KEY = "inventory.item.created";
    public static final String GOODS_REPLENISHED_ROUTING_KEY = "inventory.goods.replenished";
    
    public static final String ORDERS_EXCHANGE =
            "orders.events.exchange";

    public static final String RESERVE_STOCK_ROUTING_KEY =
            "orders.stock.reserve";
    
    public static final String STOCK_RESERVED_ROUTING_KEY =
            "inventory.stock.reserved";

    public static final String STOCK_RESERVATION_FAILED_ROUTING_KEY =
            "inventory.stock.reservation.failed";
    
    public static final String GOODS_SOLD_ROUTING_KEY =
            "orders.goods.sold";
    
    public static final String CANCEL_RESERVATION_ROUTING_KEY =
            "orders.stock.cancel";

    @Bean
    public TopicExchange inventoryExchange() {
        return new TopicExchange(INVENTORY_EXCHANGE);
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    @Bean
    public Queue itemCreatedQueue() {
        return new Queue("inventory.item.created.queue", true);
    }

    @Bean
    public Binding itemCreatedBinding() {
        return BindingBuilder
                .bind(itemCreatedQueue())
                .to(inventoryExchange())
                .with(ITEM_CREATED_ROUTING_KEY);
    }
    @Bean
    public Queue goodsReplenishedQueue() {
        return new Queue("inventory.goods.replenished.queue", true);
    }

    @Bean
    public Binding goodsReplenishedBinding() {
        return BindingBuilder
                .bind(goodsReplenishedQueue())
                .to(inventoryExchange())
                .with(GOODS_REPLENISHED_ROUTING_KEY);
    }
    @Bean
    public TopicExchange ordersExchange() {
        return new TopicExchange(ORDERS_EXCHANGE);
    }

    @Bean
    public Queue reserveStockQueue() {
        return new Queue("orders.stock.reserve.queue", true);
    }

    @Bean
    public Binding reserveStockBinding() {
        return BindingBuilder
                .bind(reserveStockQueue())
                .to(ordersExchange())
                .with(RESERVE_STOCK_ROUTING_KEY);
    }
    @Bean
    public Queue goodsSoldQueue() {
        return new Queue("orders.goods.sold.queue", true);
    }
    @Bean
    public Binding goodsSoldBinding() {
        return BindingBuilder
                .bind(goodsSoldQueue())
                .to(ordersExchange())
                .with(GOODS_SOLD_ROUTING_KEY);
    }
    @Bean
    public Queue cancelReservationQueue() {
        return new Queue("orders.stock.cancel.queue", true);
    }
    @Bean
    public Binding cancelReservationBinding() {
        return BindingBuilder
                .bind(cancelReservationQueue())
                .to(ordersExchange())
                .with(CANCEL_RESERVATION_ROUTING_KEY);
    }
    
}