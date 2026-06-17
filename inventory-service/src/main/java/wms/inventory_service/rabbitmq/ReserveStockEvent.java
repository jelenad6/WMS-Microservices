package wms.inventory_service.rabbitmq;

import java.io.Serializable;
import java.math.BigDecimal;

public class ReserveStockEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String shipmentRequestNumber;
    private String itemCode;
    private BigDecimal quantity;

    public ReserveStockEvent() {
    }

    public String getShipmentRequestNumber() {
        return shipmentRequestNumber;
    }

    public void setShipmentRequestNumber(String shipmentRequestNumber) {
        this.shipmentRequestNumber = shipmentRequestNumber;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }
}