package wms.orders_service.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "shipment_requests")
public class ShipmentRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String shipmentRequestNumber;

    private String orderNumber;

    private BigDecimal totalAmount;

    private LocalDateTime paymentDeadline;

    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    public enum ShipmentStatus {
        CREATED,
        RESERVATION_PENDING,
        READY_FOR_PAYMENT,
        FAILED,
        PAID,
        EXPIRED,
        CANCELLED
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShipmentRequestNumber() {
		return shipmentRequestNumber;
	}

	public void setShipmentRequestNumber(String shipmentRequestNumber) {
		this.shipmentRequestNumber = shipmentRequestNumber;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public LocalDateTime getPaymentDeadline() {
		return paymentDeadline;
	}

	public void setPaymentDeadline(LocalDateTime paymentDeadline) {
		this.paymentDeadline = paymentDeadline;
	}

	public ShipmentStatus getStatus() {
		return status;
	}

	public void setStatus(ShipmentStatus status) {
		this.status = status;
	}

    
}