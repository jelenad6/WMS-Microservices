package wms.inventory_service.rabbitmq;

import java.io.Serializable;

public class StockReservedEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String shipmentRequestNumber;

    public StockReservedEvent() {
    }

    public StockReservedEvent(String shipmentRequestNumber) {
        this.shipmentRequestNumber = shipmentRequestNumber;
    }

    public String getShipmentRequestNumber() {
        return shipmentRequestNumber;
    }

    public void setShipmentRequestNumber(String shipmentRequestNumber) {
        this.shipmentRequestNumber = shipmentRequestNumber;
    }
}