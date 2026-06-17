package wms.orders_service.rabbitmq;

import java.io.Serializable;
import java.math.BigDecimal;

public class CancelReservationEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String shipmentRequestNumber;
    private String itemCode;
    private BigDecimal quantity;
    
    public CancelReservationEvent() {
    }

    public CancelReservationEvent(
            String shipmentRequestNumber,
            String itemCode,
            BigDecimal quantity) {

        this.shipmentRequestNumber = shipmentRequestNumber;
        this.itemCode = itemCode;
        this.quantity = quantity;
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

    public String getShipmentRequestNumber() {
        return shipmentRequestNumber;
    }

    public void setShipmentRequestNumber(String shipmentRequestNumber) {
        this.shipmentRequestNumber = shipmentRequestNumber;
    }
}