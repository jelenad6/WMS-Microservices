package wms.inventory_service.service;

import org.springframework.stereotype.Service;

import wms.inventory_service.repository.StockRepository;
import java.math.BigDecimal;

import wms.inventory_service.model.Stock;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import wms.inventory_service.model.DispatchNote;
import wms.inventory_service.model.DispatchNoteItem;
import wms.inventory_service.repository.DispatchNoteRepository;

@Service
public class StockService {

    private final StockRepository stockRepository;
    private final DispatchNoteRepository dispatchNoteRepository;

    public StockService(StockRepository stockRepository,
    		DispatchNoteRepository dispatchNoteRepository) {
    	
        this.stockRepository = stockRepository;
        this.dispatchNoteRepository = dispatchNoteRepository;
    }
    
    public boolean reserveStock(
            String itemCode,
            BigDecimal quantity) {

        Stock stock = stockRepository
                .findByItemCode(itemCode)
                .stream()
                .findFirst()
                .orElse(null);

        if (stock == null) {
            return false;
        }

        if (stock.getAvailableQuantity()
                .compareTo(quantity) < 0) {
            return false;
        }

        stock.setAvailableQuantity(
                stock.getAvailableQuantity().subtract(quantity)
        );

        stock.setReservedQuantity(
                stock.getReservedQuantity().add(quantity)
        );

        stockRepository.save(stock);

        return true;
    }
    
    public void completeSale(
    		String shipmentRequestNumber,
            String itemCode,
            BigDecimal quantity) {

        Stock stock = stockRepository
                .findByItemCode(itemCode)
                .stream()
                .findFirst()
                .orElseThrow();

        stock.setReservedQuantity(
                stock.getReservedQuantity().subtract(quantity)
        );

        stock.setTotalQuantity(
                stock.getTotalQuantity().subtract(quantity)
        );

        stockRepository.save(stock);
        
        DispatchNote dispatchNote = new DispatchNote();

        dispatchNote.setDispatchNoteNumber(
                "DN-" + UUID.randomUUID().toString().substring(0, 8));

        dispatchNote.setShipmentRequestNumber(shipmentRequestNumber);
        dispatchNote.setDispatchDate(LocalDate.now());
        
        DispatchNoteItem dispatchNoteItem = new DispatchNoteItem();

        dispatchNoteItem.setItemCode(itemCode);
        dispatchNoteItem.setQuantity(quantity);
        dispatchNoteItem.setUnitMeasure("PCS");
        dispatchNoteItem.setDispatchNote(dispatchNote);

        ArrayList<DispatchNoteItem> items = new ArrayList<>();
        items.add(dispatchNoteItem);

        dispatchNote.setItems(items);

        dispatchNoteRepository.save(dispatchNote);
    }
    
    public void cancelReservation(
            String itemCode,
            BigDecimal quantity) {

        Stock stock = stockRepository
                .findByItemCode(itemCode)
                .stream()
                .findFirst()
                .orElseThrow();

        stock.setReservedQuantity(
                stock.getReservedQuantity().subtract(quantity)
        );

        stock.setAvailableQuantity(
                stock.getAvailableQuantity().add(quantity)
        );

        stockRepository.save(stock);
    }
}