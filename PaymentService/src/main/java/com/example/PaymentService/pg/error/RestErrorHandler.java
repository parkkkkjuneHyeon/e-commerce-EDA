package com.example.PaymentService.pg.error;

import com.example.PaymentService.exception.payment.PaymentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class RestErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(RestErrorHandler.class);

    public static void handleClientError(HttpRequest request, ClientHttpResponse response) throws IOException {

        try {
            String body = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
            Integer status = response.getStatusCode().value();

            HttpStatus httpStatus = status.equals(401) ? HttpStatus.UNAUTHORIZED : HttpStatus.BAD_REQUEST;

            log.error("토스페이먼츠 클라이언트 에러 : ({}) : {}", response.getStatusCode().value(), body);
            throw PaymentException.builder()
                    .httpStatus(httpStatus)
                    .message(body)
                    .build();

        }catch (IOException e) {
            log.error("에러 응답 가져오기 실패");
            throw PaymentException.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("응답 읽기 실패")
                    .build();
        }
    }

    public static void handleServerError(HttpRequest request, ClientHttpResponse response) throws IOException {

        try {
            String body = new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8);
            log.error("토스페이먼츠 서버 에러 : ({}) : {}", response.getStatusCode().value(), body);



            throw PaymentException.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message(body)
                    .build();

        }catch (IOException e) {
            log.error("에러 응답 가져오기 실패");
            throw PaymentException.builder()
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("응답 읽기 실패")
                    .build();
        }
    }
}
