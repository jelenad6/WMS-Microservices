package wms.orders_service.dto;

import java.math.BigDecimal;

public class CreateOrderItemRequest {

    private String itemCode;
    private BigDecimal quantity;

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