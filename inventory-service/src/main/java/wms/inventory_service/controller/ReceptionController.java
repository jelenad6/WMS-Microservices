package wms.inventory_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import wms.inventory_service.dto.ReceptionRequest;
import wms.inventory_service.service.ReceptionService;

@RestController
@RequestMapping("/receptions")
public class ReceptionController {

    private final ReceptionService receptionService;

    public ReceptionController(ReceptionService receptionService) {
        this.receptionService = receptionService;
    }

    @PostMapping
    public ResponseEntity<String> receiveGoods(
            @RequestBody ReceptionRequest request) {

        receptionService.receiveGoods(request);

        return ResponseEntity.ok("Goods received successfully");
    }
}