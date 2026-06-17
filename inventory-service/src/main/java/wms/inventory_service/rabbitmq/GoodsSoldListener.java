package wms.inventory_service.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import wms.inventory_service.service.StockService;

@Component
public class GoodsSoldListener {
	
	private final StockService stockService;

	public GoodsSoldListener(StockService stockService) {
	    this.stockService = stockService;
	}

    @RabbitListener(queues = "orders.goods.sold.queue")
    public void handleGoodsSold(GoodsSoldEvent event) {

        System.out.println(
                "INVENTORY RECEIVED GOODS SOLD: "
                        + event.getShipmentRequestNumber()
        );
        
        stockService.completeSale(
        		event.getShipmentRequestNumber(),
                event.getItemCode(),
                event.getQuantity()
        );
    }
}