package wms.orders_service.model;


import java.math.BigDecimal;

import jakarta.persistence.*;

@Entity
@Table(name = "catalog_items")

public class CatalogItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String itemCode;
	private String name;
	private String unitMeasure;
	
	private BigDecimal averagePurchasePrice;
	private BigDecimal marginPercentage;
	private BigDecimal sellingPrice;
	
	
	public BigDecimal getAveragePurchasePrice() {
		return averagePurchasePrice;
	}
	public void setAveragePurchasePrice(BigDecimal averagePurchasePrice) {
		this.averagePurchasePrice = averagePurchasePrice;
	}
	public BigDecimal getMarginPercentage() {
		return marginPercentage;
	}
	public void setMarginPercentage(BigDecimal marginPercentage) {
		this.marginPercentage = marginPercentage;
	}
	public BigDecimal getSellingPrice() {
		return sellingPrice;
	}
	public void setSellingPrice(BigDecimal sellingPrice) {
		this.sellingPrice = sellingPrice;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
