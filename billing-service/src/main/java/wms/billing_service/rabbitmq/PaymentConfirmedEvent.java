package wms.billing_service.rabbitmq;

import java.time.LocalDate;

public class PaymentConfirmedEvent {

    private String shipmentRequestNumber;
    private LocalDate paymentDate;

    public PaymentConfirmedEvent() {
    }

    public PaymentConfirmedEvent(String shipmentRequestNumber, LocalDate paymentDate) {
        this.shipmentRequestNumber = shipmentRequestNumber;
        this.paymentDate = paymentDate;
    }

	public String getShipmentRequestNumber() {
		return shipmentRequestNumber;
	}

	public void setShipmentRequestNumber(String shipmentRequestNumber) {
		this.shipmentRequestNumber = shipmentRequestNumber;
	}

	public LocalDate getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(LocalDate paymentDate) {
		this.paymentDate = paymentDate;
	}

    
}