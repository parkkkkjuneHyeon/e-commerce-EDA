package com.phiworks.OrderService.service;

import com.phiworks.OrderService.dto.catalog.StockCountDTO;
import com.phiworks.OrderService.dto.delivery.DeliveryRequestDTO;
import com.phiworks.OrderService.dto.orders.OrderRequestDTO;
import com.phiworks.OrderService.dto.orders.OrderResponseDTO;
import com.phiworks.OrderService.dto.payments.PaymentRequestDTO;
import com.phiworks.OrderService.feignclien.CatalogFeignClient;
import com.phiworks.OrderService.feignclien.DeliveryFeignClient;
import com.phiworks.OrderService.feignclien.PaymentFeignClient;
import com.phiworks.OrderService.model.entity.OrdersEntity;
import com.phiworks.OrderService.model.repository.OrderRepository;
import com.phiworks.OrderService.types.OrderStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final PaymentFeignClient paymentFeignClient;
    private final DeliveryFeignClient deliveryFeignClient;
    private final CatalogFeignClient catalogFeignClient;


    @Transactional
    public OrderResponseDTO finishOrder(OrderRequestDTO requestDTO) {
        // 1. 결제 시작
        var paymentRequestDTO = PaymentRequestDTO.builder()
                .orderId(requestDTO.getOrderId())
                .paymentKey(requestDTO.getPaymentKey())
                .amount(requestDTO.getAmount())
                .build();

        var paymentDTO = paymentFeignClient.paymentConfirm(paymentRequestDTO);

        // 2. 결제 완료 확인.
        Objects.requireNonNull(paymentDTO);

        // 3. catalog 상품 재고 업데이트
        // 상품 재고 확인 후 (이미 사이트에 재고가 보여지는데 거기서 걸러지지않을까? 보류)
        var metadata = paymentDTO.getMetadata();
        Long productId = Long.parseLong(metadata.get("productId").toString());
        Long productCount = Long.parseLong(metadata.get("productCount").toString());
        Long memberId = Long.parseLong(metadata.get("memberId").toString());
        String deliveryCompany = metadata.get("deliveryCompany").toString();

        var stockCountDTO = StockCountDTO.builder()
                .decrementStockCount(productCount)
                .build();

        var productResponseDTO = catalogFeignClient.decreaseStockCount(
                productId,
                stockCountDTO
        );
        var deliveryResponseDTO = deliveryFeignClient.getMemberAddress(memberId).getFirst();
        // 4. 배송 시작
        log.info("finishOrder -> deliveryCompany : {}", deliveryCompany);
        var deliveryRequestDTO = DeliveryRequestDTO.builder()
                .orderId(requestDTO.getOrderId())
                .productName(productResponseDTO.getProductName())
                .address(deliveryResponseDTO.getAddress())
                .deliveryCompany(deliveryCompany)
                .productCount(productCount)
                .build();

        var deliverResponseDTO = deliveryFeignClient.processDelivery(deliveryRequestDTO);
        Objects.requireNonNull(deliverResponseDTO);
        // 5. 결과값 리턴.

        var ordersEntity = OrdersEntity.builder()
                .id(requestDTO.getOrderId())
                .orderStatus(OrderStatus.DELIVERY_REQUESTED)
                .orderName(paymentDTO.getOrderName())
                .productCount(productCount)
                .amount(requestDTO.getAmount())
                .memberId(memberId)
                .deliveryId(deliverResponseDTO.getDeliveryId())
                .paymentKey(paymentDTO.getPaymentKey())
                .createdAt(paymentDTO.getApprovedAt())
                .build();

        var savedOrdersEntity = orderRepository.save(ordersEntity);

        return OrderResponseDTO.builder()
                .id(savedOrdersEntity.getId())
                .orderName(savedOrdersEntity.getOrderName())
                .productCount(savedOrdersEntity.getProductCount())
                .amount(savedOrdersEntity.getAmount())
                .memberId(savedOrdersEntity.getMemberId())
                .orderStatus(savedOrdersEntity.getOrderStatus())
                .paymentKey(savedOrdersEntity.getPaymentKey())
                .deliveryId(savedOrdersEntity.getDeliveryId())
                .createdAt(savedOrdersEntity.getCreatedAt())
                .build();
    }


}
