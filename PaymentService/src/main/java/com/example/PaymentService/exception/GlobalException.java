package com.example.PaymentService.exception;

import com.example.PaymentService.exception.dto.ExceptionResponseDTO;
import com.example.PaymentService.exception.payment.PaymentException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;


@Slf4j
@ControllerAdvice
public class GlobalException {



    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ExceptionResponseDTO> paymentException(
            PaymentException e, HttpServletRequest request
    ) {
        var response = ExceptionResponseDTO.builder()
                .message(e.getMessage())
                .httpStatus(e.getHttpStatus())
                .errorURI(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, response.getHttpStatus());
    }


    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponseDTO> handleCustomException(
            CustomException e, HttpServletRequest request
    ) {

        log.error("CustomException : {} ", e.getMessage(), e);

        var response = ExceptionResponseDTO.builder()
                .message(e.getMessage())
                .httpStatus(e.getHttpStatus())
                .errorURI(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponseDTO> handleRuntimeException(
            RuntimeException e, HttpServletRequest request
    ) {

        log.error("RuntimeException : {} ", e.getMessage(), e);

        var response = ExceptionResponseDTO.builder()
                .httpStatus(HttpStatus.BAD_REQUEST)
                .message("시스템 작동중 에러")
                .errorURI(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, response.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> handleException(
            Exception e, HttpServletRequest request
    ) {
        log.error("Exception : {} ", e.getMessage(), e);

        var response = ExceptionResponseDTO.builder()
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .message("서버 에러 발생")
                .errorURI(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(response, response.getHttpStatus());
    }
}
