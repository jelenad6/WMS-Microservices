package wms.inventory_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

import wms.inventory_service.model.*;
import wms.inventory_service.service.ItemService;
import wms.inventory_service.dto.NewItemRequest;

@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }
    @PostMapping
    public Item createItem(@RequestBody NewItemRequest request) {
        return itemService.createItem(request);
    }
    @GetMapping("/{itemCode}/state")
    public List<Stock> getItemState(@PathVariable String itemCode) {
        return itemService.getItemState(itemCode);
    }
    
    
}
