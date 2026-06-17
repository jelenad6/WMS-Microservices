package wms.inventory_service.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import wms.inventory_service.service.StockService;


@Component
public class OrdersEventListener {
	
	private final StockService stockService;
	private final InventoryEventPublisher inventoryEventPublisher;
	
	public OrdersEventListener(
	        StockService stockService,
	        InventoryEventPublisher inventoryEventPublisher) {

	    this.stockService = stockService;
	    this.inventoryEventPublisher = inventoryEventPublisher;
	}
	
	@RabbitListener(queues = "orders.stock.reserve.queue")
	public void handleReserveStock(ReserveStockEvent event) {

	    System.out.println(
	            "INVENTORY RECEIVED RESERVE STOCK: "
	                    + event.getItemCode()
	                    + " qty=" + event.getQuantity()
	    );

	    boolean reserved = stockService.reserveStock(
	            event.getItemCode(),
	            event.getQuantity()
	    );

	    System.out.println("RESERVATION RESULT: " + reserved);

	    if (reserved) {

	        inventoryEventPublisher.publishStockReserved(
	                new StockReservedEvent(
	                        event.getShipmentRequestNumber()
	                )
	        );

	    } else {

	        inventoryEventPublisher.publishStockReservationFailed(
	                new StockReservationFailedEvent(
	                        event.getShipmentRequestNumber(),
	                        "Insufficient stock"
	                )
	        );
	    }
	}
}