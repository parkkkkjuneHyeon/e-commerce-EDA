package com.example.PaymentService.pg;

import com.example.PaymentService.dto.PaymentCancelDTO;
import com.example.PaymentService.dto.PaymentDTO;
import com.example.PaymentService.dto.PaymentRequestDTO;
import com.example.PaymentService.dto.cancel.CancelDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentGatewayService {

    PaymentDTO confirmPayment(PaymentRequestDTO paymentRequestDTO);
    PaymentDTO findByPaymentKey(PaymentRequestDTO paymentRequestDTO);
    List<CancelDTO> paymentCancel(PaymentCancelDTO paymentCancelDTO);
}
