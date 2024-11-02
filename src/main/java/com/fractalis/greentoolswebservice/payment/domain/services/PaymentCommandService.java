package com.fractalis.greentoolswebservice.payment.domain.services;

import com.fractalis.greentoolswebservice.payment.domain.model.aggregates.Payment;
import com.fractalis.greentoolswebservice.payment.domain.model.valueobjects.DatePayment;
import com.fractalis.greentoolswebservice.payment.domain.model.valueobjects.Plan;

public interface PaymentCommandService
{
    Payment createPayment(Long userId, Plan plan, DatePayment datePayment ,String cardNumber);
    void deletePayment(Long paymentId);
}
