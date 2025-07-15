package com.example.PaymentService.usecase;

import com.example.PaymentService.dto.PaymentCancelDTO;
import com.example.PaymentService.dto.PaymentDTO;
import com.example.PaymentService.dto.PaymentRequestDTO;
import com.example.PaymentService.dto.PaymentResponseDTO;
import com.example.PaymentService.dto.cancel.CancelDTO;
import com.example.PaymentService.exception.payment.FailSavedPaymentException;
import com.example.PaymentService.exception.payment.NotEqualsAmountException;
import com.example.PaymentService.exception.payment.PaymentException;
import com.example.PaymentService.pg.PaymentGatewayService;
import com.example.PaymentService.service.PaymentService;
import com.example.PaymentService.type.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
        log.info("confirmPayment 시작");
        var paymentsEntity = paymentService.processPayment(paymentRequestDTO);

        try {

            var paymentDTO = paymentGatewayService.confirmPayment(paymentRequestDTO);
            log.info("compareAmount 시작");
            compareAmount(paymentsEntity.getTotalAmount(), paymentDTO.getTotalAmount());

            paymentService.updatePaymentCompleted(paymentsEntity, paymentDTO);
            log.info("confirmPayment 완료");
            return paymentDTO;

        }
        catch (NotEqualsAmountException | FailSavedPaymentException e) {
            //금액 및 저장 문제로 다시 결제 취소
            var cancelRequestDTO = PaymentCancelDTO.builder()
                    .paymentKey(paymentRequestDTO.getPaymentKey())
                    .cancelAmount(paymentRequestDTO.getAmount())
                    .cancelReason(e.getMessage())
                    .build();
            HttpStatus httpStatus = e.getHttpStatus();
            log.error("NotEqualsAmountException | FailSavedPaymentException {}", e.getMessage(), e);
            paymentCancel(cancelRequestDTO);
            throw PaymentException.builder()
                    .httpStatus(httpStatus)
                    .message(e.getMessage())
                    .build();

        }catch (PaymentException e) {
            HttpStatus httpStatus = e.getHttpStatus();
            log.error("confirmPayment {}", e.getMessage(), e);

            throw PaymentException.builder()
                    .httpStatus(httpStatus)
                    .message(e.getMessage())
                    .build();
        }
    }

    public List<CancelDTO> paymentCancel(PaymentCancelDTO paymentCancelDTO) {

        validateCancelableStatus(paymentCancelDTO.getPaymentKey());

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

        }catch (PaymentException e) {
            var paymentResponseDTO = paymentService
                    .findByPaymentKey(paymentCancelDTO.getPaymentKey());

            log.error("결제 취소 실패 : {}",paymentResponseDTO.getPaymentStatus(), e);

            throw PaymentException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("결제 취소 실패 : " + paymentResponseDTO.getPaymentStatus())
                    .build();
        }
    }

    public PaymentResponseDTO findByPaymentKey(PaymentRequestDTO paymentRequestDTO) {
        var paymentResponseDTO = paymentService.findByPaymentKey(paymentRequestDTO);

        var paymentDTO = paymentGatewayService.findByPaymentKey(paymentRequestDTO);

        return paymentResponseDTO;
    }

    protected void compareAmount(Long requestAmount, Long confirmResponseAmount) {
        log.info("compareAmount requestAmount:{} and confirmResponseAmount:{}"
                , requestAmount, confirmResponseAmount);

        if(!requestAmount.equals(confirmResponseAmount)) {
            throw NotEqualsAmountException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("결제정보가 맞지 않습니다.")
                    .build();

        }
    }

    protected void validateCancelableStatus(String paymentKey) {
        var cancelPaymentEntity = paymentService.findByPaymentKey(paymentKey);
        PaymentStatus status = cancelPaymentEntity.getPaymentStatus();

        if(status.equals(PaymentStatus.CANCELED))
            throw PaymentException.builder()
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .message("이미 결제가 취소되었습니다. : " + status.name())
                    .build();
    }
}
