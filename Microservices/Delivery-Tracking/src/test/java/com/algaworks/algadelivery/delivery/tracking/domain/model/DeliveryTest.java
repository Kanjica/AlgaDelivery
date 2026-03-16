package com.algaworks.algadelivery.delivery.tracking.domain.model;

import com.algaworks.algadelivery.delivery.tracking.domain.exception.DomainException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryTest {

    @Test
    public void shouldChangeStatusToPlaced(){
        Delivery delivery = Delivery.draft();

        delivery.editPreparetionDetails(createdValidPreparationDetails());

        delivery.place();

        assertEquals(DeliveryStatus.WAITING_FOR_COURIER, delivery.getStatus());
        assertNotNull(delivery.getPlacedAt());
    }

    @Test
    public void shouldNotPlaced(){
        Delivery delivery = Delivery.draft();

        assertThrows(DomainException.class, delivery::place);

        assertEquals(DeliveryStatus.DRAFT, delivery.getStatus());
        assertNull(delivery.getPlacedAt());
    }

    private Delivery.PreparationDetails createdValidPreparationDetails() {

        ContactPoint sender = ContactPoint.builder()
                .zipCode("00000-00")
                .street("Rua abc")
                .number("15")
                .complement("Perto da casa do chápeu")
                .name("Thiago")
                .phone("(71)98235-0970")
                .build();

        ContactPoint recipient = ContactPoint.builder()
                .zipCode("15469-96")
                .street("Rua xyz")
                .number("66")
                .complement("")
                .name("Marllom")
                .phone("(71) 93334-1930")
                .build();

        return Delivery.PreparationDetails.builder()
                .sender(sender)
                .recipient(recipient)
                .distanceFee(new BigDecimal("10.00"))
                .courierPayout(new BigDecimal("5.00"))
                .expectedDeliveryTime(Duration.ofHours(5))
                .build();
    }
}