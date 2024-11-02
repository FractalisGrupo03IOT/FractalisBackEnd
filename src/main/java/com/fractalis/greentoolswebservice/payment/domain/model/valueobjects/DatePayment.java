package com.fractalis.greentoolswebservice.payment.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

import java.time.LocalDate;

@Embeddable
public record DatePayment(LocalDate purchaseDate, LocalDate expirationDate) {

    public DatePayment(LocalDate purchaseDate) {
        this(purchaseDate, purchaseDate.plusMonths(1)); // Agregamos 1 mes a la fecha de compra
    }
}

