package wms.orders_service.controller;

import org.springframework.web.bind.annotation.*;

import wms.orders_service.dto.CreateOrderRequest;
import wms.orders_service.model.CustomerOrder;
import wms.orders_service.model.ShipmentRequest;
import wms.orders_service.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public CustomerOrder createOrder(
            @RequestBody CreateOrderRequest request) {

        return orderService.createOrder(request);
    }
    @PostMapping("/{orderNumber}/confirm")
    public ShipmentRequest confirmOrder(
            @PathVariable String orderNumber) {

        return orderService.createShipmentRequest(orderNumber);
    }
}