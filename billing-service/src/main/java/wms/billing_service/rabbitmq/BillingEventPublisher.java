package wms.billing_service.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class BillingEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public BillingEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publishPaymentConfirmed(
            PaymentConfirmedEvent event) {

        rabbitTemplate.convertAndSend(
                BillingRabbitMQConfig.BILLING_EXCHANGE,
                BillingRabbitMQConfig.PAYMENT_CONFIRMED_ROUTING_KEY,
                event
        );
    }
}