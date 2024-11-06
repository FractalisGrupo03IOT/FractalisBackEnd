package com.fractalis.greentoolswebservice.payment.interfaces.rest;

import com.fractalis.greentoolswebservice.account.domain.model.aggregates.User;
import com.fractalis.greentoolswebservice.account.domain.services.UserQueryService;
import com.fractalis.greentoolswebservice.payment.domain.model.aggregates.Payment;
import com.fractalis.greentoolswebservice.payment.domain.model.valueobjects.DatePayment;
import com.fractalis.greentoolswebservice.payment.domain.services.PaymentCommandService;
import com.fractalis.greentoolswebservice.payment.domain.services.PaymentQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin(origins = "**", maxAge = 3600)
@RestController
@RequestMapping(value = "/api/v1", produces = APPLICATION_JSON_VALUE)
@Tag(name = "Payments", description = "All payment related endpoints")
public class PaymentController {

    private final PaymentCommandService paymentCommandService;
    private final PaymentQueryService paymentQueryService;
    private final UserQueryService userQueryService;

    /**
     * Constructor
     *
     * @param paymentCommandService The {@link PaymentCommandService} service
     * @param paymentQueryService The {@link PaymentQueryService} service
     * @param userQueryService The {@link UserQueryService} service
     */
    @Autowired
    public PaymentController(PaymentCommandService paymentCommandService, PaymentQueryService paymentQueryService, UserQueryService userQueryService) {
        this.paymentCommandService = paymentCommandService;
        this.paymentQueryService = paymentQueryService;
        this.userQueryService = userQueryService;
    }

    /**
     * Get all the payments
     *
     * @return The list of {@link Payment} payments
     */
    @Operation(summary = "Get all payments")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Payments founded")})
    @GetMapping("/payment")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentQueryService.getAllPayments();
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    /**
     * Get a payment by its id
     *
     * @param id The payment id
     * @return The {@link Payment} payment
     */
    @Operation(summary = "Get a payment by its id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Payment founded")})
    @GetMapping("/payments/{id}")
    public ResponseEntity<Payment> getPaymentById(
            @Parameter(name = "paymentId", description = "Payment id", required = true) @PathVariable Long id) {
        Optional<Payment> payment = paymentQueryService.getPaymentById(id);
        return payment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Create a payment
     *
     * @param userId The user id
     * @param paymentRequest The payment body
     * @return The just created {@link Payment} payment
     */
    @Operation(summary = "Create a payment")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Payment created")})
    @PostMapping("/payment")
    public ResponseEntity<Payment> createPayment(
            @RequestBody @Schema(description = "Payment body") Payment paymentRequest,
            @Parameter(name = "userId", description = "User id", required = true) @RequestParam Long userId) {
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

    /**
     * Delete a payment
     *
     * @param id The payment id
     * @return The object {@link HttpStatus} status
     */
    @Operation(summary = "Delete a payment")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Payment deleted")})
    @DeleteMapping("/payment/{id}")
    public ResponseEntity<Void> deletePayment(
            @Parameter(name = "paymentId", description = "Payment id", required = true) @PathVariable Long id) {
        paymentCommandService.deletePayment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
