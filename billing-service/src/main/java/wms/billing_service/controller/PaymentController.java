package wms.billing_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import wms.billing_service.dto.PayShipmentRequest;
import wms.billing_service.model.Payment;
import wms.billing_service.service.PaymentService;

@RestController
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payments")
    public Payment payShipment(
            @RequestBody PayShipmentRequest request) {

        return paymentService.payShipment(request);
    }
}