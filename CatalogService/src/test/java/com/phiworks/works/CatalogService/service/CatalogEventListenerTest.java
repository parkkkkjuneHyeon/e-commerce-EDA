package com.phiworks.works.CatalogService.service;

import com.google.protobuf.InvalidProtocolBufferException;
import edaordersystem.protobuf.EdaMessage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CatalogEventListenerTest {

    @Mock
    private KafkaTemplate<String, byte[]> kafkaTemplate;

    @Mock
    private CatalogService catalogService;

    @InjectMocks
    private CatalogEventListener catalogEventListener;


    @Test
    void catalogDecrementRequestListener_상품재고_최신화_후_배송요청() throws InvalidProtocolBufferException {
        //give
        var stockCountMessage = EdaMessage.stockCountRequestV1.newBuilder()
                .setProductId(1L)
                .setDecrementStockCount(2)
                .setOrderId("ord-123")
                .setOrderName("이어폰")
                .setMemberId(1)
                .setDeliveryCompany("lotte")
                .setAddress("서울시 동동구")
                .setPaymentKey("pay-123")
                .setAmount(10000L)
                .build();
        var deliveryRequestMessage = EdaMessage.deliveryRequestV1.newBuilder()
                .setOrderId("ord-123")
                .setOrderName("이어폰")
                .setAddress("서울시 동동구")
                .setDeliveryCompany("lotte")
                .setProductCount(2)
                .setMemberId(1)
                .setPaymentKey("pay-123")
                .setAmount(10000L)
                .build();

        //when
        catalogEventListener.catalogDecrementRequestListener(stockCountMessage.toByteArray());

        //then
        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<byte[]> valueCaptor = ArgumentCaptor.forClass(byte[].class);
        Mockito.verify(kafkaTemplate).send(keyCaptor.capture(), valueCaptor.capture());
        var deliveryCaptor = EdaMessage.deliveryRequestV1.parseFrom(valueCaptor.getValue());

        //해당 토픽이 맞는지?
        assertThat(keyCaptor.getValue())
                .isEqualTo("delivery-request");

        //해당 토픽으로 올바르게 데이터를 보내는지?
        assertThat(deliveryCaptor.getOrderId())
                .isEqualTo(deliveryRequestMessage.getOrderId());

        assertThat(deliveryCaptor.getOrderName())
                .isEqualTo(deliveryRequestMessage.getOrderName());

        assertThat(deliveryCaptor.getAddress())
                .isEqualTo(deliveryRequestMessage.getAddress());

        assertThat(deliveryCaptor.getDeliveryCompany())
                .isEqualTo(deliveryRequestMessage.getDeliveryCompany());

        assertThat(deliveryCaptor.getProductCount())
                .isEqualTo(deliveryRequestMessage.getProductCount());

        assertThat(deliveryCaptor.getMemberId())
                .isEqualTo(deliveryRequestMessage.getMemberId());

        assertThat(deliveryCaptor.getPaymentKey())
                .isEqualTo(deliveryRequestMessage.getPaymentKey());

        assertThat(deliveryCaptor.getAmount())
                .isEqualTo(deliveryRequestMessage.getAmount());

    }
}