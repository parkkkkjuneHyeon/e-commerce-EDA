package com.example.PaymentService.service;

import com.example.PaymentService.dto.PaymentDTO;
import com.example.PaymentService.dto.PaymentRequestDTO;
import com.google.protobuf.InvalidProtocolBufferException;
import edaordersystem.protobuf.EdaMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.ZonedDateTime;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentEventListenerTest {

    @Mock
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    private PaymentEventListener paymentEventListener;

    @Test
    void paymentConfirmRequestEventListener_토스결제_확인_후_오더에_응답을_보낸다() throws InvalidProtocolBufferException {
        //give
        String orderId = UUID.randomUUID().toString();
        String paymentKey = "pen-123";
        long amount = 1000L;

        var paymentRequestV1Message = EdaMessage.paymentRequestV1.newBuilder()
                .setOrderId(orderId)
                .setPaymentKey(paymentKey)
                .setAmount(amount)
                .build();

        var paymentRequestDTO = PaymentRequestDTO.builder()
                .orderId(orderId)
                .paymentKey(paymentKey)
                .amount(amount)
                .build();

        var mockPaymentDTO = PaymentDTO.builder()
                .orderId(orderId)
                .orderName("이어폰")
                .paymentKey(paymentKey)
                .approvedAt(ZonedDateTime.now())
                .metadata(Map.of())
                .build();

        //when
        when(paymentService.confirmPayment(any(PaymentRequestDTO.class)))
                .thenReturn(mockPaymentDTO);

        paymentEventListener.paymentConfirmRequestEventListener(paymentRequestV1Message.toByteArray());

        //then
        //토스결제 확인요청을 올바르게 보냈는지
        ArgumentCaptor<PaymentRequestDTO> paymentRequestDTOCaptor = ArgumentCaptor
                .forClass(PaymentRequestDTO.class);
        verify(paymentService).confirmPayment(paymentRequestDTOCaptor.capture());

        assertThat(paymentRequestDTOCaptor.getValue().getOrderId()).isEqualTo(paymentRequestDTO.getOrderId());
        assertThat(paymentRequestDTOCaptor.getValue().getPaymentKey()).isEqualTo(paymentRequestDTO.getPaymentKey());
        assertThat(paymentRequestDTOCaptor.getValue().getAmount()).isEqualTo(paymentRequestDTO.getAmount());

        //kafka producer 데이터를 올바르게 보냈는지.
        ArgumentCaptor<String> topicCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<byte[]> payloadCaptor = ArgumentCaptor.forClass(byte[].class);
        verify(kafkaTemplate).send(topicCaptor.capture(), payloadCaptor.capture());
        var captorPayloadMessage = EdaMessage.paymentResponseV1.parseFrom(payloadCaptor.getValue());

        assertThat(topicCaptor.getValue()).isEqualTo("payment-confirm-response");
        assertThat(captorPayloadMessage.getOrderId()).isEqualTo(orderId);
        assertThat(captorPayloadMessage.getPaymentKey()).isEqualTo(paymentKey);
        assertThat(captorPayloadMessage.getAmount()).isEqualTo(amount);
        assertThat(captorPayloadMessage.getOrderName()).isEqualTo(mockPaymentDTO.getOrderName());
        assertThat(captorPayloadMessage.getApprovedAt().toString()).isEqualTo(mockPaymentDTO.getApprovedAt().toString());
        assertThat(captorPayloadMessage.getMetadataMap()).isEqualTo(mockPaymentDTO.getMetadata());




    }
}