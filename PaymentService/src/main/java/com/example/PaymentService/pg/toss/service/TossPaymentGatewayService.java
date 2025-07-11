package com.example.PaymentService.pg.toss.service;

import com.example.PaymentService.dto.PaymentCancelDTO;
import com.example.PaymentService.dto.PaymentDTO;
import com.example.PaymentService.dto.PaymentRequestDTO;
import com.example.PaymentService.dto.cancel.CancelDTO;
import com.example.PaymentService.pg.PaymentGatewayService;
import com.example.PaymentService.pg.toss.properties.TossProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TossPaymentGatewayService implements PaymentGatewayService {

    private final RestClient restClient;
    private final TossProperties tossProperties;


    public PaymentDTO confirmPayment(PaymentRequestDTO paymentRequestDTO) {
        String authorizations = getAuthorizationSecretKey();

        //pg사에 따라 대응으로 adapter 패턴으로 차후 개선
        ResponseEntity<PaymentDTO> paymentResponseEntity = restClient.post()
                .uri(tossProperties.getPaymentUrl() + "/confirm")
                .headers(header -> {
                    header.add("Authorization", authorizations);
                    header.add("Content-Type", "application/json");
                })
                .body(paymentRequestDTO)
                .retrieve()
                .toEntity(PaymentDTO.class);

        // 결제를 승인하면 결제수단에서 금액이 차감돼요.
        log.info("payment 결재 url : {} ", tossProperties.getPaymentUrl() + "/confirm");
        log.info("payment 결재 상태 : {} ", paymentResponseEntity.getBody().getStatus());
        log.info("payment 결재 응답 : {} ", Objects.requireNonNull(paymentResponseEntity.getBody()));

        return paymentResponseEntity.getBody();
    }

    @Override
    public PaymentDTO findByPaymentKey(PaymentRequestDTO paymentRequestDTO) {
        String authorizations = getAuthorizationSecretKey();

        log.info("paymentKey : {} ", paymentRequestDTO.getPaymentKey());

        ResponseEntity<PaymentDTO> paymentResponse = restClient
                .get()
                .uri(tossProperties.getPaymentUrl() + "/" + paymentRequestDTO.getPaymentKey())
                .headers(header -> {
                    header.add("Authorization", authorizations);
                })
                .retrieve()
                .toEntity(PaymentDTO.class);

        log.info("payment PaymentKey 조회 응답 : {} ", Objects.requireNonNull(paymentResponse.getBody()));

        return paymentResponse.getBody();
    }

    @Override
    public List<CancelDTO> paymentCancel(PaymentCancelDTO paymentCancelDTO) {
        String authorization = getAuthorizationSecretKey();

        ResponseEntity<PaymentDTO> paymentDTOResponse = restClient.post()
                .uri(tossProperties.getPaymentUrl() + "/" + paymentCancelDTO.getPaymentKey() + "/cancel")
                .headers(header -> {
                    header.add("Authorization", authorization);
                    header.add("Content-Type", "application/json");
                })
                .body(paymentCancelDTO)
                .retrieve()
                .toEntity(PaymentDTO.class);

        log.info("payment cancel 응답 : {}", paymentDTOResponse.getBody());
        var paymentDTO = paymentDTOResponse.getBody();

        return Objects.requireNonNull(paymentDTO).getCancels();
    }


    private String getAuthorizationSecretKey() {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((tossProperties.getWidgetSecretKey() + ":").getBytes(StandardCharsets.UTF_8));
        return "Basic " + new String(encodedBytes);
    }

}
