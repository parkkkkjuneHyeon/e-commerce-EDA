package com.example.PaymentService.dto;

import com.example.PaymentService.dto.cancel.CancelDTO;
import com.example.PaymentService.dto.card.CardDTO;
import com.example.PaymentService.dto.cash.CashReceiptDTO;
import com.example.PaymentService.dto.cash.CashReceiptHistoryDTO;
import com.example.PaymentService.dto.virtualaccount.VirtualAccountDTO;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {

    // 기본 결제 정보
    private String version;
    private String paymentKey;
    private String type;
    private String orderId;
    private String orderName;
    private String mId;
    private String currency;
    private String method;

    // 금액 정보
    private Long totalAmount;
    private Long balanceAmount;
    private Long suppliedAmount;
    private Long vat;
    private Long taxFreeAmount;
    private Integer taxExemptionAmount;

    // 상태 및 시간 정보
    private String status;
    private ZonedDateTime requestedAt;              // yyyy-MM-dd'T'HH:mm:ss±hh:mm ex)2022-01-01T00:00:00+09:00
    private ZonedDateTime approvedAt;               // yyyy-MM-dd'T'HH:mm:ss±hh:mm ex)2022-01-01T00:00:00+09:00

    // 기타 정보
    private Boolean useEscrow;
    private Boolean cultureExpense;
    private String lastTransactionKey;
    private Boolean isPartialCancelable;
    private String country;
    private String secret;

    // 결제수단별 정보
    private CardDTO card;
    private VirtualAccountDTO virtualAccount;
    private Transfer transfer;
    private MobilePhone mobilePhone;
    private GiftCertificate giftCertificate;
    private EasyPay easyPay;

    // 부가 정보
    private List<CancelDTO> cancels;
    private CashReceiptDTO cashReceipt;
    private List<CashReceiptHistoryDTO> cashReceipts;
    private Receipt receipt;
    private Checkout checkout;
    private Discount discount;
    private Failure failure;
    private Map<String, String> metadata;


    /**
     * 계좌이체 결제 정보
     */
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Transfer {
        private String bankCode;
        private String settlementStatus;
    }

    /**
     * 휴대폰 결제 정보
     */
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MobilePhone {
        private String customerMobilePhone;
        private String settlementStatus;
        private String receiptUrl;
    }

    /**
     * 상품권 결제 정보
     */
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GiftCertificate {
        private String approveNo;
        private String settlementStatus;
    }

    /**
     * 간편결제 정보
     */
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EasyPay {
        private String provider;
        private Long amount;
        private Long discountAmount;
    }

    /**
     * 영수증 정보
     */
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Receipt {
        private String url;
    }

    /**
     * 결제창 정보
     */
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Checkout {
        private String url;
    }

    /**
     * 즉시할인 정보
     */
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Discount {
        private Integer amount;
    }

    /**
     * 실패 정보
     */
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Failure {
        private String code;
        private String message;
    }
}