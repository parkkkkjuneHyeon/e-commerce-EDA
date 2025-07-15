package com.example.PaymentService.pg.toss.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TossProperties {
    @Value("${spring.toss.widget.secretkey}")
    private String widgetSecretKey;
    @Value("${spring.toss.url}")
    private String paymentUrl;
}
