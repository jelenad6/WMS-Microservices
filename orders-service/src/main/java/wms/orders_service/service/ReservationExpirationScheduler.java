package wms.orders_service.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ReservationExpirationScheduler {

    private final OrderService orderService;

    public ReservationExpirationScheduler(
            OrderService orderService) {

        this.orderService = orderService;
    }

    @Scheduled(fixedRate = 60000)
    public void checkExpiredReservations() {

        orderService.cancelExpiredReservations();
    }
}