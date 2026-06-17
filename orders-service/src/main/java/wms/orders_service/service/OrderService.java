package wms.orders_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
import java.util.List;


import wms.orders_service.dto.CreateOrderItemRequest;
import wms.orders_service.dto.CreateOrderRequest;
import wms.orders_service.model.CustomerOrder;
import wms.orders_service.model.OrderItem;
import wms.orders_service.model.ShipmentRequest;
import wms.orders_service.rabbitmq.CancelReservationEvent;
import wms.orders_service.rabbitmq.GoodsSoldEvent;
import wms.orders_service.rabbitmq.OrdersEventPublisher;
import wms.orders_service.rabbitmq.ReserveStockEvent;
import wms.orders_service.model.CatalogItem;
import wms.orders_service.repository.CatalogItemRepository;
import wms.orders_service.repository.CustomerOrderRepository;
import wms.orders_service.repository.ShipmentRequestRepository;
import java.time.LocalDate;

import wms.orders_service.model.Invoice;
import wms.orders_service.model.InvoiceItem;
import wms.orders_service.repository.InvoiceRepository;


@Service
public class OrderService {

    private final CustomerOrderRepository customerOrderRepository;
    private final CatalogItemRepository catalogItemRepository;
    private final ShipmentRequestRepository shipmentRequestRepository;
    private final OrdersEventPublisher ordersEventPublisher;
    private final InvoiceRepository invoiceRepository;

    public OrderService(
            CustomerOrderRepository customerOrderRepository,
            CatalogItemRepository catalogItemRepository,
            ShipmentRequestRepository shipmentRequestRepository,
            OrdersEventPublisher ordersEventPublisher,
            InvoiceRepository invoiceRepository) {

        this.customerOrderRepository = customerOrderRepository;
        this.catalogItemRepository = catalogItemRepository;
        this.shipmentRequestRepository = shipmentRequestRepository;
        this.ordersEventPublisher = ordersEventPublisher;
        this.invoiceRepository = invoiceRepository;
    }
    
    public CustomerOrder createOrder(CreateOrderRequest request) {

        CustomerOrder order = new CustomerOrder();

        order.setOrderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8));
        order.setCustomerCode(request.getCustomerCode());
        order.setCustomerName(request.getCustomerName());
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setStatus(CustomerOrder.OrderStatus.CREATED);
        order.setCreatedAt(LocalDateTime.now());

        ArrayList<OrderItem> orderItems = new ArrayList<>();

        for (CreateOrderItemRequest itemRequest : request.getItems()) {

            OrderItem item = new OrderItem();
            
            CatalogItem catalogItem =
                    catalogItemRepository
                            .findByItemCode(itemRequest.getItemCode())
                            .orElseThrow();

            item.setItemCode(itemRequest.getItemCode());
            item.setItemName(catalogItem.getName());
            item.setUnitMeasure(catalogItem.getUnitMeasure());
            item.setQuantity(itemRequest.getQuantity());
            item.setUnitPrice(
                    catalogItem.getSellingPrice());
            item.setOrder(order);

            orderItems.add(item);
        }

        order.setItems(orderItems);

        return customerOrderRepository.save(order);
    }
    
    public ShipmentRequest createShipmentRequest(String orderNumber) {

        CustomerOrder order = customerOrderRepository
                .findByOrderNumber(orderNumber)
                .orElseThrow();

        ShipmentRequest shipmentRequest = new ShipmentRequest();

        shipmentRequest.setShipmentRequestNumber(
                "SR-" + UUID.randomUUID().toString().substring(0, 8));

        shipmentRequest.setOrderNumber(order.getOrderNumber());

        shipmentRequest.setStatus(
                ShipmentRequest.ShipmentStatus.RESERVATION_PENDING);

        shipmentRequest.setPaymentDeadline(
                LocalDateTime.now().plusMinutes(15));

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItem item : order.getItems()) {
            BigDecimal itemTotal = item.getQuantity()
                    .multiply(item.getUnitPrice());

            totalAmount = totalAmount.add(itemTotal);
        }

        shipmentRequest.setTotalAmount(totalAmount);

        ShipmentRequest savedShipmentRequest =
                shipmentRequestRepository.save(shipmentRequest);

        for (OrderItem item : order.getItems()) {

            ReserveStockEvent event = new ReserveStockEvent(
                    savedShipmentRequest.getShipmentRequestNumber(),
                    item.getItemCode(),
                    item.getQuantity()
            );

            ordersEventPublisher.publishReserveStock(event);
        }

        return savedShipmentRequest;
    }
    public void markShipmentRequestAsReadyForPayment(String shipmentRequestNumber) {

        ShipmentRequest shipmentRequest = shipmentRequestRepository
                .findByShipmentRequestNumber(shipmentRequestNumber)
                .orElseThrow();

        shipmentRequest.setStatus(
                ShipmentRequest.ShipmentStatus.READY_FOR_PAYMENT
        );

        shipmentRequestRepository.save(shipmentRequest);
    }
    public void markShipmentRequestAsFailed(
            String shipmentRequestNumber) {

        ShipmentRequest shipmentRequest = shipmentRequestRepository
                .findByShipmentRequestNumber(shipmentRequestNumber)
                .orElseThrow();

        shipmentRequest.setStatus(
                ShipmentRequest.ShipmentStatus.FAILED
        );

        shipmentRequestRepository.save(shipmentRequest);
    }
    
    @Transactional
    public void markShipmentRequestAsPaid(String shipmentRequestNumber) {

        ShipmentRequest shipmentRequest = shipmentRequestRepository
                .findByShipmentRequestNumber(shipmentRequestNumber)
                .orElseThrow();

        shipmentRequest.setStatus(ShipmentRequest.ShipmentStatus.PAID);

        shipmentRequestRepository.save(shipmentRequest);

        CustomerOrder order = customerOrderRepository
                .findByOrderNumber(shipmentRequest.getOrderNumber())
                .orElseThrow();

        Invoice invoice = new Invoice();

        invoice.setInvoiceNumber(
                "INV-" + UUID.randomUUID().toString().substring(0, 8));

        invoice.setShipmentRequestNumber(
                shipmentRequest.getShipmentRequestNumber());

        invoice.setOrderNumber(
                shipmentRequest.getOrderNumber());

        invoice.setPaymentDate(LocalDate.now());

        invoice.setTotalAmount(
                shipmentRequest.getTotalAmount());

        ArrayList<InvoiceItem> invoiceItems = new ArrayList<>();

        for (OrderItem orderItem : order.getItems()) {

            InvoiceItem invoiceItem = new InvoiceItem();

            invoiceItem.setItemCode(orderItem.getItemCode());
            invoiceItem.setItemName(orderItem.getItemName());
            invoiceItem.setQuantity(orderItem.getQuantity());
            invoiceItem.setUnitMeasure(orderItem.getUnitMeasure());
            invoiceItem.setUnitPrice(orderItem.getUnitPrice());
            invoiceItem.setLineTotal(
                    orderItem.getQuantity().multiply(orderItem.getUnitPrice()));

            invoiceItem.setInvoice(invoice);

            invoiceItems.add(invoiceItem);
        }

        invoice.setItems(invoiceItems);

        invoiceRepository.save(invoice);

        for (OrderItem item : order.getItems()) {
            ordersEventPublisher.publishGoodsSold(
                    new GoodsSoldEvent(
                            shipmentRequest.getShipmentRequestNumber(),
                            item.getItemCode(),
                            item.getQuantity()
                    )
            );
        }
    }
    @Transactional
    public void cancelExpiredReservations() {

        List<ShipmentRequest> expiredRequests =
                shipmentRequestRepository
                        .findByStatusAndPaymentDeadlineBefore(
                                ShipmentRequest.ShipmentStatus.READY_FOR_PAYMENT,
                                LocalDateTime.now()
                        );

        for (ShipmentRequest shipmentRequest : expiredRequests) {

            CustomerOrder order = customerOrderRepository
                    .findByOrderNumber(
                            shipmentRequest.getOrderNumber())
                    .orElseThrow();

            for (OrderItem item : order.getItems()) {

                ordersEventPublisher.publishCancelReservation(
                        new CancelReservationEvent(
                                shipmentRequest.getShipmentRequestNumber(),
                                item.getItemCode(),
                                item.getQuantity()
                        )
                );
            }

            shipmentRequest.setStatus(
                    ShipmentRequest.ShipmentStatus.CANCELLED);

            shipmentRequestRepository.save(shipmentRequest);
        }
    }
}