package com.fractalis.greentoolswebservice.payment.domain.services;

import com.fractalis.greentoolswebservice.payment.domain.model.aggregates.Payment;
import com.fractalis.greentoolswebservice.payment.domain.model.valueobjects.DatePayment;
import com.fractalis.greentoolswebservice.payment.domain.model.valueobjects.Plan;

import java.util.List;
import java.util.Optional;

public interface PaymentQueryService
{
    List<Payment> getAllPayments();
    Optional<Payment> getPaymentById(Long id);
}
