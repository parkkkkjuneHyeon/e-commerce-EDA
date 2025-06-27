package com.example.PaymentService.controller;

import com.example.PaymentService.dto.PaymentConfirmDTO;
import com.example.PaymentService.dto.PaymentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentsApiController {

    private final RestTemplate restTemplate;

    @Value("${spring.toss.widget.secretkey}")
    private String widgetSecretKey;


    @RequestMapping(value = "/confirm")
    public ResponseEntity<HttpStatus> confirmPayment(
            @RequestBody PaymentConfirmDTO paymentConfirmDTO
    ) {

        // 토스페이먼츠 API는 시크릿 키를 사용자 ID로 사용하고, 비밀번호는 사용하지 않습니다.
        // 비밀번호가 없다는 것을 알리기 위해 시크릿 키 뒤에 콜론을 추가합니다.

        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedBytes = encoder.encode((widgetSecretKey + ":").getBytes(StandardCharsets.UTF_8));
        String authorizations = "Basic " + new String(encodedBytes);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", authorizations);

        // 결제를 승인하면 결제수단에서 금액이 차감돼요.
        try {
            URL url = new URL("https://api.tosspayments.com/v1/payments/confirm");

            HttpEntity<PaymentConfirmDTO> request = new HttpEntity<>(paymentConfirmDTO, headers);

            ResponseEntity<PaymentDTO> responseEntity = restTemplate.exchange(
                    url.toURI(),
                    HttpMethod.POST,
                    request,
                    PaymentDTO.class
            );

            log.info("payment : {} ", Objects.requireNonNull(responseEntity.getBody()));

        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
