package com.fractalis.greentoolswebservice.payment.interfaces.rest;

import com.fractalis.greentoolswebservice.account.domain.model.aggregates.User;
import com.fractalis.greentoolswebservice.account.domain.services.UserQueryService;
import com.fractalis.greentoolswebservice.inventory.domain.model.aggregates.Inventory;
import com.fractalis.greentoolswebservice.payment.domain.model.aggregates.Payment;
import com.fractalis.greentoolswebservice.payment.domain.model.valueobjects.DatePayment;
import com.fractalis.greentoolswebservice.payment.domain.services.PaymentCommandService;
import com.fractalis.greentoolswebservice.payment.domain.services.PaymentQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1")
public class PaymentController {

    private final PaymentCommandService paymentCommandService;
    private final PaymentQueryService paymentQueryService;
    private final UserQueryService userQueryService;

    @Autowired
    public PaymentController(PaymentCommandService paymentCommandService, PaymentQueryService paymentQueryService, UserQueryService userQueryService) {
        this.paymentCommandService = paymentCommandService;
        this.paymentQueryService = paymentQueryService;
        this.userQueryService = userQueryService;
    }

    @GetMapping("/payment")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentQueryService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @GetMapping("/payments/{id}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
        Optional<Payment> payment = paymentQueryService.getPaymentById(id);
        return payment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/payment")
    public ResponseEntity<Payment> createPayment(@RequestBody Payment paymentRequest, @RequestParam Long userId) {
        Optional<User> user = userQueryService.getUserById(userId);

        if(user.isPresent()) {
            Payment payment = paymentCommandService.createPayment(
                    userId,
                    paymentRequest.getPlan(),
                    new DatePayment(LocalDate.now()),
                    paymentRequest.getCardNumber()
            );
            return new ResponseEntity<>(payment, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("/payment/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentCommandService.deletePayment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
