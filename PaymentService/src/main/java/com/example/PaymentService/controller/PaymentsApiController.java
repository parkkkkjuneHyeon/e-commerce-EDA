package com.example.PaymentService.controller;

import com.example.PaymentService.dto.PaymentCancelDTO;
import com.example.PaymentService.dto.PaymentRequestDTO;
import com.example.PaymentService.dto.PaymentResponseDTO;
import com.example.PaymentService.dto.cancel.CancelDTO;
import com.example.PaymentService.service.PaymentUsecaseService;
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

    private final PaymentUsecaseService paymentUsecaseService;


//    @PostMapping("/confirm")
//    public ResponseEntity<PaymentDTO> confirmPayment(
//            @RequestBody PaymentRequestDTO paymentRequestDTO
//    ) {
//        var responseEntityPaymentDTO = paymentService
//                .confirmPayment(paymentRequestDTO);
//
//        return ResponseEntity.ok(responseEntityPaymentDTO);
//    }

    @PostMapping("/payment")
    public ResponseEntity<PaymentResponseDTO> paymentKey(
            @RequestBody PaymentRequestDTO paymentRequestDTO
    ) {
        var PaymentDTO = paymentUsecaseService
                .findByPaymentKey(paymentRequestDTO);

        return ResponseEntity.ok(PaymentDTO);
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<List<CancelDTO>> cancelPayment(
            @RequestBody PaymentCancelDTO paymentCancelDTO
    ) {
        var cancelDTOList = paymentUsecaseService.paymentCancel(paymentCancelDTO);

        return ResponseEntity.ok(cancelDTOList);
    }
}
