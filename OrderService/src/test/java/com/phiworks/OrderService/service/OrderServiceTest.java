package com.phiworks.OrderService.service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.phiworks.OrderService.dto.orders.OrderRequestDTO;
import com.phiworks.OrderService.model.repository.OrderRepository;
import edaordersystem.protobuf.EdaMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    private OrderRepository orderRepository;

    @Mock
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @InjectMocks
    OrderService orderService;


    @Test
    void finishOrder_카프카_메시지_전송_확인() throws InvalidProtocolBufferException {
        //give
        var orderRequestDTO = OrderRequestDTO.builder()
                .orderId("ord-123")
                .paymentKey("ten_1234")
                .amount(1000L)
                .build();
        //when
        orderService.finishOrder(orderRequestDTO);

        //then
        ArgumentCaptor<String> topicArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<byte[]> messageArgumentCaptor = ArgumentCaptor.forClass(byte[].class);


        verify(kafkaTemplate).send(topicArgumentCaptor.capture(), messageArgumentCaptor.capture());
        // 토픽 검증
        assertThat(topicArgumentCaptor.getValue()).isEqualTo("payment-confirm-request");

        // 메시지 내용 검증
        var sentMessage = EdaMessage.paymentRequestV1.parseFrom(messageArgumentCaptor.getValue());
        assertThat(sentMessage.getOrderId()).isEqualTo("ord-123");
        assertThat(sentMessage.getPaymentKey()).isEqualTo("ten_1234");
        assertThat(sentMessage.getAmount()).isEqualTo(1000L);
    }
}