package wms.orders_service.rabbitmq;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;

@Configuration
public class RabbitMQConfig {
	
	public static final String INVENTORY_EXCHANGE =
	        "inventory.events.exchange";

	public static final String STOCK_RESERVED_ROUTING_KEY =
	        "inventory.stock.reserved";

	public static final String STOCK_RESERVATION_FAILED_ROUTING_KEY =
	        "inventory.stock.reservation.failed";
	
	public static final String BILLING_EXCHANGE =
	        "billing.events.exchange";

	public static final String PAYMENT_CONFIRMED_ROUTING_KEY =
	        "billing.payment.confirmed";
	
	public static final String ORDERS_EXCHANGE =
	        "orders.events.exchange";

	public static final String GOODS_SOLD_ROUTING_KEY =
	        "orders.goods.sold";
	
	public static final String RESERVE_STOCK_ROUTING_KEY =
	        "orders.stock.reserve";
	
	public static final String CANCEL_RESERVATION_ROUTING_KEY =
	        "orders.stock.cancel";
	public static final String GOODS_REPLENISHED_ROUTING_KEY =
	        "inventory.goods.replenished";

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Bean
    public TopicExchange inventoryExchange() {
        return new TopicExchange(INVENTORY_EXCHANGE);
    }

    @Bean
    public Queue stockReservedQueue() {
        return new Queue("inventory.stock.reserved.queue", true);
    }

    @Bean
    public Queue stockReservationFailedQueue() {
        return new Queue("inventory.stock.reservation.failed.queue", true);
    }

    @Bean
    public Binding stockReservedBinding() {
        return BindingBuilder
                .bind(stockReservedQueue())
                .to(inventoryExchange())
                .with(STOCK_RESERVED_ROUTING_KEY);
    }

    @Bean
    public Binding stockReservationFailedBinding() {
        return BindingBuilder
                .bind(stockReservationFailedQueue())
                .to(inventoryExchange())
                .with(STOCK_RESERVATION_FAILED_ROUTING_KEY);
    }
    @Bean
    public TopicExchange billingExchange() {
        return new TopicExchange(BILLING_EXCHANGE);
    }

    @Bean
    public Queue paymentConfirmedQueue() {
        return new Queue("billing.payment.confirmed.queue", true);
    }

    @Bean
    public Binding paymentConfirmedBinding() {
        return BindingBuilder
                .bind(paymentConfirmedQueue())
                .to(billingExchange())
                .with(PAYMENT_CONFIRMED_ROUTING_KEY);
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
}