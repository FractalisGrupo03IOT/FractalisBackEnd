package com.fractalis.greentoolswebservice.payment.application.internal.commandservices;

import com.fractalis.greentoolswebservice.payment.domain.model.aggregates.Payment;
import com.fractalis.greentoolswebservice.payment.domain.model.valueobjects.DatePayment;
import com.fractalis.greentoolswebservice.payment.domain.model.valueobjects.Plan;
import com.fractalis.greentoolswebservice.payment.domain.services.PaymentCommandService;
import com.fractalis.greentoolswebservice.payment.infraestructure.persistence.jpa.repositories.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Service
public class PaymentCommandServiceImpl implements PaymentCommandService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentCommandServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public void deletePayment(Long paymentId) {
        paymentRepository.deleteById(paymentId);
    }

    @Override
    public Payment createPayment(Long userId, Plan plan, DatePayment datePayment, String cardNumber) {
        // Creamos un nuevo DatePayment con la fecha actual de compra
        DatePayment newDatePayment = new DatePayment(LocalDate.now()); // Fecha de compra actual

        Payment payment = new Payment(userId, plan, newDatePayment, cardNumber);
        return paymentRepository.save(payment);
    }
}
