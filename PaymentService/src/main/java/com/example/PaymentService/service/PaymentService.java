package com.example.PaymentService.service;

import com.example.PaymentService.dto.PaymentCancelDTO;
import com.example.PaymentService.dto.PaymentDTO;
import com.example.PaymentService.dto.PaymentRequestDTO;
import com.example.PaymentService.dto.PaymentResponseDTO;
import com.example.PaymentService.entity.PaymentsEntity;
import com.example.PaymentService.exception.payment.FailSavedPaymentException;
import com.example.PaymentService.exception.payment.PaymentException;
import com.example.PaymentService.repository.PaymentsRepository;
import com.example.PaymentService.type.PaymentMethod;
import com.example.PaymentService.type.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentsRepository paymentsRepository;


    @Transactional
    public void updatePaymentCompleted(PaymentsEntity processPaymentsEntity, PaymentDTO paymentDTO) {
        try {
            var donePaymentsEntity = PaymentDTO.of(processPaymentsEntity, paymentDTO);

            paymentsRepository.save(donePaymentsEntity);

            log.info("updatePaymentCompleted 완료");
        }catch (Exception e) {
            log.error("updatePaymentCompleted save 실패 {}", e);

            throw FailSavedPaymentException.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("결제 과정 중 이상이 생겨 저장을 실패했습니다.")
                    .build();
        }
    }

    @Transactional
    public PaymentsEntity processPayment(PaymentRequestDTO paymentRequestDTO) {
        try {
            var paymentEntity = PaymentsEntity.builder()
                    .orderId(paymentRequestDTO.getOrderId())
                    .totalAmount(paymentRequestDTO.getAmount())
                    .paymentStatus(PaymentStatus.READY)
                    .paymentKey(paymentRequestDTO.getPaymentKey())
                    .build();

            log.info("processPayment : {}", paymentEntity);
            return paymentsRepository.save(paymentEntity);

        }catch (RuntimeException e) {

            return paymentsRepository
                    .findByPaymentKey(paymentRequestDTO.getPaymentKey())
                    .map(entity -> {
                        if(entity.getPaymentStatus().equals(PaymentStatus.READY))
                            return entity;

                        log.error(
                                "이미 결제가 생성 되었습니다. " +
                                "\npaymentKey : {} " +
                                "\npaymentStatus : {}",
                                entity.getPaymentKey(),
                                entity.getPaymentStatus()
                        );
                        throw PaymentException.builder()
                                .httpStatus(HttpStatus.BAD_REQUEST)
                                .message(entity.getPaymentStatus().toString() + "이므로 잘못된 요청입니다.")
                                .build();
                    })
                    .orElseThrow(() ->
                            PaymentException.builder()
                            .httpStatus(HttpStatus.BAD_REQUEST)
                            .message("데이터 정합성 오류")
                            .build()
                    );
        }
    }

    public PaymentResponseDTO findByPaymentKey(PaymentRequestDTO paymentRequestDTO) {
        var paymentEntity = paymentsRepository.findByPaymentKey(paymentRequestDTO.getPaymentKey())
                .orElseThrow(() -> new IllegalArgumentException("wrong payment key"));

        return PaymentResponseDTO.of(paymentEntity);
    }

    public PaymentResponseDTO findByPaymentKey(String paymentKey) {
        var paymentEntity = paymentsRepository.findByPaymentKey(paymentKey)
                .orElseThrow(() -> new IllegalArgumentException("wrong payment key"));

        return PaymentResponseDTO.of(paymentEntity);
    }

    public void paymentCancel(PaymentCancelDTO paymentCancelDTO) {
        paymentsRepository.softDeleteByPaymentKey(
                paymentCancelDTO.getMemberId(),
                paymentCancelDTO.getPaymentKey(),
                PaymentStatus.CANCELED,
                paymentCancelDTO.getCancelDate()
        );
    }

}
