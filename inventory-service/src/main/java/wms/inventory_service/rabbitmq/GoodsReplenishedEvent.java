package wms.inventory_service.rabbitmq;

import java.io.Serializable;
import java.math.BigDecimal;

public class GoodsReplenishedEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String warehouseCode;
    private String itemCode;
    private BigDecimal quantity;
    private BigDecimal purchasePrice;

    public GoodsReplenishedEvent() {
    }

    public GoodsReplenishedEvent(String warehouseCode, String itemCode, BigDecimal quantity, BigDecimal purchasePrice) {
        this.warehouseCode = warehouseCode;
        this.itemCode = itemCode;
        this.quantity = quantity;
        this.purchasePrice = purchasePrice;
    }

    public String getWarehouseCode() {
        return warehouseCode;
    }

    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
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

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}
}