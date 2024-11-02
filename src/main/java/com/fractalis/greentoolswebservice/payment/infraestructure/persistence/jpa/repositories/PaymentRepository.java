package com.fractalis.greentoolswebservice.payment.infraestructure.persistence.jpa.repositories;

import com.fractalis.greentoolswebservice.payment.domain.model.aggregates.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
