package com.phiworks.OrderService.feignclien;

import com.phiworks.OrderService.dto.payments.PaymentDTO;
import com.phiworks.OrderService.dto.payments.PaymentRequestDTO;
import com.phiworks.OrderService.dto.payments.cancel.CancelDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "paymentClient", url = "http://payment-service:8080")
public interface PaymentFeignClient {


    @PostMapping("/payments/confirm")
    PaymentDTO paymentConfirm(@RequestBody PaymentRequestDTO paymentRequestDTO);

    @DeleteMapping("/payments/cancel")
    List<CancelDTO> paymentCancel(@RequestBody PaymentRequestDTO paymentRequestDTO);

    @PostMapping("/payment")
    ResponseEntity<PaymentDTO> paymentKey(@RequestBody PaymentRequestDTO paymentRequestDTO);

}
