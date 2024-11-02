package com.fractalis.greentoolswebservice.payment.domain.model.aggregates;

import com.fractalis.greentoolswebservice.payment.domain.model.valueobjects.DatePayment;
import com.fractalis.greentoolswebservice.payment.domain.model.valueobjects.Plan;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long user_id;

    @Column(nullable = false)
    private Plan plan;

    @Column(nullable = false)
    private String cardNumber;

    @Embedded
    @Column(name = "date_payment")
    private DatePayment datePayment;

    public Payment(Long userId, Plan plan, DatePayment datePayment, String cardNumber) {
        this.user_id = userId;
        this.plan = plan;
        this.datePayment = datePayment;
        this.cardNumber = cardNumber;
    }
}