package wms.billing_service.rabbitmq;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;

@Configuration
public class BillingRabbitMQConfig {

    public static final String BILLING_EXCHANGE =
            "billing.events.exchange";

    public static final String PAYMENT_CONFIRMED_ROUTING_KEY =
            "billing.payment.confirmed";

    @Bean
    public TopicExchange billingExchange() {
        return new TopicExchange(BILLING_EXCHANGE);
    }
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}