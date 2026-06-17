package wms.inventory_service.model;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "dispatch_notes")
public class DispatchNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dispatchNoteNumber;

    private String shipmentRequestNumber;

    private LocalDate dispatchDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDispatchNoteNumber() {
		return dispatchNoteNumber;
	}

	public void setDispatchNoteNumber(String dispatchNoteNumber) {
		this.dispatchNoteNumber = dispatchNoteNumber;
	}

	public String getShipmentRequestNumber() {
		return shipmentRequestNumber;
	}

	public void setShipmentRequestNumber(String shipmentRequestNumber) {
		this.shipmentRequestNumber = shipmentRequestNumber;
	}

	public LocalDate getDispatchDate() {
		return dispatchDate;
	}

	public void setDispatchDate(LocalDate dispatchDate) {
		this.dispatchDate = dispatchDate;
	}
	
	@OneToMany(
	        mappedBy = "dispatchNote",
	        cascade = CascadeType.ALL,
	        orphanRemoval = true)
	private List<DispatchNoteItem> items;

	public List<DispatchNoteItem> getItems() {
		return items;
	}

	public void setItems(List<DispatchNoteItem> items) {
		this.items = items;
	}

    
}