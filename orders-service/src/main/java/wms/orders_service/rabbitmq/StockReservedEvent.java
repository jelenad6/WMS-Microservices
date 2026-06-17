package wms.orders_service.rabbitmq;

public class StockReservedEvent {

    private String shipmentRequestNumber;

    public String getShipmentRequestNumber() {
        return shipmentRequestNumber;
    }

    public void setShipmentRequestNumber(String shipmentRequestNumber) {
        this.shipmentRequestNumber = shipmentRequestNumber;
    }
}