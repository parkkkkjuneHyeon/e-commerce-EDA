package com.example.PaymentService.exception.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@ToString
public class ExceptionResponseDTO {
    private HttpStatus httpStatus;
    private String message;
    private String errorURI;
    private LocalDateTime timestamp;
}
