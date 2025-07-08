package com.example.PaymentService.service;

import com.example.PaymentService.dto.PaymentCancelDTO;
import com.example.PaymentService.dto.PaymentDTO;
import com.example.PaymentService.dto.PaymentRequestDTO;
import com.example.PaymentService.dto.cancel.CancelDTO;
import com.example.PaymentService.entity.PaymentsEntity;
import com.example.PaymentService.repository.PaymentsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class PaymentService {

    private final RestClient restClient;
    private final String widgetSecretKey;
    private final String paymentUrl;
    private final PaymentsRepository paymentsRepository;

    public PaymentService(
            @Value("${spring.toss.widget.secretkey}") String widgetSecretKey,
            @Value("${spring.toss.url}") String paymentUrl,
            RestClient restClient,
            PaymentsRepository paymentsRepository) {
        this.widgetSecretKey = widgetSecretKey;
        this.paymentUrl = paymentUrl;
        this.restClient = restClient;
        this.paymentsRepository = paymentsRepository;
    }

    @Transactional
    public PaymentDTO confirmPayment(PaymentRequestDTO paymentRequestDTO) {

        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.
        String authorizations = getAuthorizationSecretKey();

        // 결제를 승인하면 결제수단에서 금액이 차감돼요.
        log.info("payment 결재 url : {} ", paymentUrl + "/confirm");
        log.info("payment 결재 DTO : {} ", paymentRequestDTO);
        
        //pg사에 따라 대응으로 adapter 패턴으로 차후 개선
        ResponseEntity<PaymentDTO> paymentResponseEntity = restClient.post()
                .uri(paymentUrl + "/confirm")
                .headers(header -> {
                    header.add("Authorization", authorizations);
                    header.add("Content-Type", "application/json");
                })
                .body(paymentRequestDTO)
                .retrieve()
                .toEntity(PaymentDTO.class);

        log.info("payment 결재 응답 : {} ", Objects.requireNonNull(paymentResponseEntity.getBody()));

        PaymentsEntity paymentsEntity = PaymentDTO.of(paymentResponseEntity.getBody());
        paymentsEntity.setMemberId(paymentRequestDTO.getMemberId());

        paymentsRepository.save(paymentsEntity);

        return paymentResponseEntity.getBody();
    }

    public ResponseEntity<PaymentDTO> findByPaymentKey(PaymentRequestDTO paymentRequestDTO) {
        String authorizations = getAuthorizationSecretKey();

        log.info("paymentKey : {} ", paymentRequestDTO.getPaymentKey());
        log.info("authorizations : {} ", authorizations);

        ResponseEntity<PaymentDTO> paymentResponseEntity = restClient
                .get()
                .uri(paymentUrl + "/" + paymentRequestDTO.getPaymentKey())
                .headers(header -> {
                    header.add("Authorization", authorizations);
                })
                .retrieve()
                .toEntity(PaymentDTO.class);

        log.info("payment PaymentKey 조회 응답 : {} ", Objects.requireNonNull(paymentResponseEntity.getBody()));

        return paymentResponseEntity;
    }

    @Transactional
    public List<CancelDTO> paymentCancel(PaymentCancelDTO paymentCancelDTO) {
        String authorization = getAuthorizationSecretKey();

        ResponseEntity<PaymentDTO> paymentDTOResponseEntity = restClient.post()
                .uri(paymentUrl + "/" + paymentCancelDTO.getPaymentKey() + "/cancel")
                .headers(header -> {
                    header.add("Authorization", authorization);
                    header.add("Content-Type", "application/json");
                })
                .body(paymentCancelDTO)
                .retrieve()
                .toEntity(PaymentDTO.class);

        log.info("payment cancel 응답 : {}", paymentDTOResponseEntity.getBody());

        paymentsRepository.softDeleteByPaymentKey(
                paymentCancelDTO.getMemberId(),
                paymentCancelDTO.getPaymentKey(),
                ZonedDateTime.now()
        );

        var paymentDTO = paymentDTOResponseEntity.getBody();

        var cancels = Objects.requireNonNull(paymentDTO).getCancels();

        return Objects.isNull(cancels) ? new ArrayList<>() : cancels;
    }

    private String getAuthorizationSecretKey() {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        return "Basic " + new String(encodedBytes);
    }
}
