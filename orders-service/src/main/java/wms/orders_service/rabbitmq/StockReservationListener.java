package wms.orders_service.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import wms.orders_service.service.OrderService;


@Component
public class StockReservationListener {
	
	private final OrderService orderService;
	
	public StockReservationListener(OrderService orderService) {
	    this.orderService = orderService;
	}

	@RabbitListener(queues = "inventory.stock.reserved.queue")
	public void handleStockReserved(StockReservedEvent event) {

	    System.out.println("ORDERS RECEIVED STOCK RESERVED: "
	            + event.getShipmentRequestNumber());

	    orderService.markShipmentRequestAsReadyForPayment(
	            event.getShipmentRequestNumber()
	    );
	}

	@RabbitListener(queues = "inventory.stock.reservation.failed.queue")
	public void handleStockReservationFailed(
	        StockReservationFailedEvent event) {

	    System.out.println(
	            "ORDERS RECEIVED STOCK RESERVATION FAILED: "
	                    + event.getShipmentRequestNumber()
	                    + " reason=" + event.getReason());

	    orderService.markShipmentRequestAsFailed(
	            event.getShipmentRequestNumber()
	    );
	}
}