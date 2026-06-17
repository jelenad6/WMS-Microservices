package wms.orders_service.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import wms.orders_service.service.OrderService;

@Component
public class BillingEventListener {

    private final OrderService orderService;

    public BillingEventListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = "billing.payment.confirmed.queue")
    public void handlePaymentConfirmed(
            PaymentConfirmedEvent event) {

        System.out.println(
                "ORDERS RECEIVED PAYMENT CONFIRMED: "
                        + event.getShipmentRequestNumber()
        );

        orderService.markShipmentRequestAsPaid(
                event.getShipmentRequestNumber()
        );
    }
}