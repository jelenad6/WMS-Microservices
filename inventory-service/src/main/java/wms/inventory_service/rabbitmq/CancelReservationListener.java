package wms.inventory_service.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import wms.inventory_service.service.StockService;

@Component
public class CancelReservationListener {

    private final StockService stockService;

    public CancelReservationListener(StockService stockService) {
        this.stockService = stockService;
    }

    @RabbitListener(queues = "orders.stock.cancel.queue")
    public void handleCancelReservation(
            CancelReservationEvent event) {

        System.out.println(
                "INVENTORY RECEIVED CANCEL RESERVATION: "
                        + event.getShipmentRequestNumber()
        );

        stockService.cancelReservation(
                event.getItemCode(),
                event.getQuantity()
        );
    }
}