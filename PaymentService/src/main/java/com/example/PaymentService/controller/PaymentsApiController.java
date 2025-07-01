package com.example.PaymentService.controller;

import com.example.PaymentService.dto.PaymentCancelDTO;
import com.example.PaymentService.dto.PaymentDTO;
import com.example.PaymentService.dto.PaymentRequestDTO;
import com.example.PaymentService.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentsApiController {

    private final PaymentService paymentService;


    @PostMapping("/confirm")
    public ResponseEntity<PaymentDTO> confirmPayment(
            @RequestBody PaymentRequestDTO paymentRequestDTO
    ) {
        ResponseEntity<PaymentDTO> paymentDTOResponseEntity = paymentService
                .confirmPayment(paymentRequestDTO);

        return paymentDTOResponseEntity;
    }

    @PostMapping("/payment")
    public ResponseEntity<PaymentDTO> paymentKey(
            @RequestBody PaymentRequestDTO paymentRequestDTO
    ) {
        ResponseEntity<PaymentDTO> paymentDTOResponseEntity = paymentService
                .findByPaymentKey(paymentRequestDTO);

        return paymentDTOResponseEntity;
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<HttpStatus> cancelPayment(
            @RequestBody PaymentCancelDTO paymentCancelDTO
    ) {
        paymentService.paymentCancel(paymentCancelDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
