package wms.billing_service.service;

import org.springframework.stereotype.Service;

import wms.billing_service.dto.PayShipmentRequest;
import wms.billing_service.model.Payment;
import wms.billing_service.repository.PaymentRepository;
import wms.billing_service.rabbitmq.BillingEventPublisher;
import wms.billing_service.rabbitmq.PaymentConfirmedEvent;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BillingEventPublisher billingEventPublisher;

    public PaymentService(
            PaymentRepository paymentRepository,
            BillingEventPublisher billingEventPublisher) {

        this.paymentRepository = paymentRepository;
        this.billingEventPublisher = billingEventPublisher;
    }

    public Payment payShipment(PayShipmentRequest request) {

        Payment payment = new Payment();

        payment.setShipmentRequestNumber(
                request.getShipmentRequestNumber());

        payment.setPaymentDate(
                request.getPaymentDate());

        payment.setStatus(
                Payment.PaymentStatus.CONFIRMED);

        Payment savedPayment =
                paymentRepository.save(payment);

        billingEventPublisher.publishPaymentConfirmed(
                new PaymentConfirmedEvent(
                        savedPayment.getShipmentRequestNumber(),
                        savedPayment.getPaymentDate()
                )
        );

        return savedPayment;
    }
}