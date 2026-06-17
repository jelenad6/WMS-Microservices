package wms.inventory_service.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "dispatch_note_items")
public class DispatchNoteItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemCode;

    private BigDecimal quantity;

    private String unitMeasure;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "dispatch_note_id")
    
    private DispatchNote dispatchNote;

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

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getUnitMeasure() {
		return unitMeasure;
	}

	public void setUnitMeasure(String unitMeasure) {
		this.unitMeasure = unitMeasure;
	}

	public DispatchNote getDispatchNote() {
		return dispatchNote;
	}

	public void setDispatchNote(DispatchNote dispatchNote) {
		this.dispatchNote = dispatchNote;
	}
    

}