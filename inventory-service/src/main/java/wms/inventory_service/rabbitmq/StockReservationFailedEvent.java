package wms.inventory_service.rabbitmq;

import java.io.Serializable;

public class StockReservationFailedEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String shipmentRequestNumber;
    private String reason;

    public StockReservationFailedEvent() {
    }

    public StockReservationFailedEvent(String shipmentRequestNumber, String reason) {
        this.shipmentRequestNumber = shipmentRequestNumber;
        this.reason = reason;
    }

    public String getShipmentRequestNumber() {
        return shipmentRequestNumber;
    }

    public void setShipmentRequestNumber(String shipmentRequestNumber) {
        this.shipmentRequestNumber = shipmentRequestNumber;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}