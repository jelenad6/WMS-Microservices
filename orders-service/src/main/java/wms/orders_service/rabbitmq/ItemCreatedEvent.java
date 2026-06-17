package wms.orders_service.rabbitmq;

import java.io.Serializable;

public class ItemCreatedEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    private String itemCode;
    private String name;
    private String unitMeasure;

    public ItemCreatedEvent() {
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }
}