package com.fractalis.greentoolswebservice.payment.application.internal.queryservices;

import com.fractalis.greentoolswebservice.payment.domain.model.aggregates.Payment;
import com.fractalis.greentoolswebservice.payment.domain.model.valueobjects.DatePayment;
import com.fractalis.greentoolswebservice.payment.domain.model.valueobjects.Plan;
import com.fractalis.greentoolswebservice.payment.domain.services.PaymentQueryService;
import com.fractalis.greentoolswebservice.payment.infraestructure.persistence.jpa.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentQueryServiceImpl implements PaymentQueryService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentQueryServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    @Override
    public Optional<Payment> getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }


}
