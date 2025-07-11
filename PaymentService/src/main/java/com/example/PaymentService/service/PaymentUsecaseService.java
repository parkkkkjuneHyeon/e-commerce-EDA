package com.example.PaymentService.service;

import com.example.PaymentService.dto.PaymentCancelDTO;
import com.example.PaymentService.dto.PaymentDTO;
import com.example.PaymentService.dto.PaymentRequestDTO;
import com.example.PaymentService.dto.PaymentResponseDTO;
import com.example.PaymentService.dto.cancel.CancelDTO;
import com.example.PaymentService.exception.payment.PaymentException;
import com.example.PaymentService.pg.PaymentGatewayService;
import com.example.PaymentService.type.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentUsecaseService {
    private final PaymentGatewayService paymentGatewayService;
    private final PaymentService paymentService;


    public PaymentDTO confirmPayment(PaymentRequestDTO paymentRequestDTO) {
        // 결제 생성
        var paymentsEntity = paymentService.processPayment(paymentRequestDTO);

        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
        try {
            var paymentDTO = paymentGatewayService.confirmPayment(paymentRequestDTO);

            compareAmount(paymentsEntity.getTotalAmount(), paymentDTO.getTotalAmount());

            paymentService.updatePaymentCompleted(paymentsEntity, paymentDTO);

            return paymentDTO;

        }catch (Exception e) {

            throw new RuntimeException("결제 처리 실패", e);
        }
    }

    @Transactional
    public List<CancelDTO> paymentCancel(PaymentCancelDTO paymentCancelDTO) {
        try {
            var cancels = paymentGatewayService.paymentCancel(paymentCancelDTO);

            cancels.forEach(cancel -> {

                var paymentStatus = PaymentStatus.getPaymentStatus(cancel.getCancelStatus());

                if(paymentStatus.equals(PaymentStatus.CANCELED)) {
                    var cancelDTO = PaymentCancelDTO.builder()
                            .memberId(paymentCancelDTO.getMemberId())
                            .paymentKey(paymentCancelDTO.getPaymentKey())
                            .cancelDate(cancel.getCanceledAt())
                            .build();
                    paymentService.paymentCancel(cancelDTO);
                }
            });
            return Objects.isNull(cancels) ? new ArrayList<CancelDTO>() : cancels;

        }catch (Exception e) {

            var paymentResponseDTO = paymentService
                    .findByPaymentKey(paymentCancelDTO.getPaymentKey());

            throw new RuntimeException("결제 취소 실패 : " + paymentResponseDTO.getPaymentStatus());
        }
    }

    public PaymentResponseDTO findByPaymentKey(PaymentRequestDTO paymentRequestDTO) {
        var paymentResponseDTO = paymentService.findByPaymentKey(paymentRequestDTO);

        var paymentDTO = paymentGatewayService.findByPaymentKey(paymentRequestDTO);

        return paymentResponseDTO;
    }

    protected void compareAmount(Long requestAmount, Long confirmResponseAmount) {
        if(!requestAmount.equals(confirmResponseAmount))
            throw PaymentException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("결제정보가 맞지 않습니다.")
                    .build();
    }
}
