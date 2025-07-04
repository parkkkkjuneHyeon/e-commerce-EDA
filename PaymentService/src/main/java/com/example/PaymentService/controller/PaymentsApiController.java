package com.example.PaymentService.controller;

import com.example.PaymentService.dto.PaymentCancelDTO;
import com.example.PaymentService.dto.PaymentDTO;
import com.example.PaymentService.dto.PaymentRequestDTO;
import com.example.PaymentService.dto.cancel.CancelDTO;
import com.example.PaymentService.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        var responseEntityPaymentDTO = paymentService
                .confirmPayment(paymentRequestDTO);

        return responseEntityPaymentDTO;
    }

    @PostMapping("/payment")
    public ResponseEntity<PaymentDTO> paymentKey(
            @RequestBody PaymentRequestDTO paymentRequestDTO
    ) {
        var responseEntityPaymentDTO = paymentService
                .findByPaymentKey(paymentRequestDTO);

        return responseEntityPaymentDTO;
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<List<CancelDTO>> cancelPayment(
            @RequestBody PaymentCancelDTO paymentCancelDTO
    ) {
        var cancelDTOList = paymentService.paymentCancel(paymentCancelDTO);

        return ResponseEntity.ok(cancelDTOList);
    }
}
