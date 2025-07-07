package com.phiworks.OrderService.dto.payments;

import com.phiworks.OrderService.dto.payments.cancel.CancelDTO;
import com.phiworks.OrderService.dto.payments.card.CardDTO;
import com.phiworks.OrderService.dto.payments.cash.CashReceiptDTO;
import com.phiworks.OrderService.dto.payments.cash.CashReceiptHistoryDTO;
import com.phiworks.OrderService.dto.payments.virtualaccount.VirtualAccountDTO;
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
    private String paymentKey;                      // 결제의 키값입니다. 최대 길이는 200자입니다. 결제를 식별하는 역할로, 중복되지 않는 고유한 값입니다. 결제 데이터 관리를 위해 반드시 저장해야 합니다. 결제 상태가 변해도 값이 유지됩니다. 결제 승인, 결제 조회, 결제 취소 API에 사용합니다.
    private String type;                            // 결제 타입 정보입니다. NORMAL(일반결제), BILLING(자동결제), BRANDPAY(브랜드페이) 중 하나입니다.
    private String orderId;                         // 주문번호입니다. 결제 요청에서 내 상점이 직접 생성한 영문 대소문자, 숫자, 특수문자 -, _로 이루어진 6자 이상 64자 이하의 문자열입니다. 각 주문을 식별하는 역할로, 결제 데이터 관리를 위해 반드시 저장해야 합니다. 결제 상태가 변해도 orderId는 유지됩니다.
    private String orderName;                       // 구매상품입니다. 예를 들면 생수 외 1건 같은 형식입니다. 최대 길이는 100자입니다.
    private String mId;                             // 상점아이디(MID)입니다. 토스페이먼츠에서 발급합니다. 최대 길이는 14자입니다.
    private String currency;                        // 결제할 때 사용한 통화입니다.
    private String method;                          // 결제수단입니다. 카드, 가상계좌, 간편결제, 휴대폰, 계좌이체, 문화상품권, 도서문화상품권, 게임문화상품권 중 하나입니다.

    // 금액 정보
    private Long totalAmount;                       // 총 결제 금액입니다. 결제가 취소되는 등 결제 상태가 변해도 최초에 결제된 결제 금액으로 유지됩니다.
    private Long balanceAmount;                     // 취소할 수 있는 금액(잔고)입니다. 이 값은 결제 취소나 부분 취소가 되고 나서 남은 값입니다. 결제 상태 변화에 따라 vat, suppliedAmount, taxFreeAmount, taxExemptionAmount와 함께 값이 변합니다.
    private Long suppliedAmount;                    // 공급가액입니다. 결제 취소 및 부분 취소가 되면 공급가액도 일부 취소되어 값이 바뀝니다.
    private Long vat;                               // 부가세입니다. 결제 취소 및 부분 취소가 되면 부가세도 일부 취소되어 값이 바뀝니다. (결제 금액 amount - 면세 금액 taxFreeAmount) / 11 후 소수점 첫째 자리에서 반올림해서 계산합니다.
    private Long taxFreeAmount;                     // 결제 금액 중 면세 금액입니다. 결제 취소 및 부분 취소가 되면 면세 금액도 일부 취소되어 값이 바뀝니다.
    private Integer taxExemptionAmount;             // 과세를 제외한 결제 금액(컵 보증금 등)입니다. 이 값은 결제 취소 및 부분 취소가 되면 과세 제외 금액도 일부 취소되어 값이 바뀝니다.

    // 상태 및 시간 정보
    /*
    결제 처리 상태입니다. 아래와 같은 상태 값을 가질 수 있습니다. 상태 변화 흐름이 궁금하다면 흐름도를 살펴보세요.

    - READY: 결제를 생성하면 가지게 되는 초기 상태입니다. 인증 전까지는 READY 상태를 유지합니다.

    - IN_PROGRESS: 결제수단 정보와 해당 결제수단의 소유자가 맞는지 인증을 마친 상태입니다. 결제 승인 API를 호출하면 결제가 완료됩니다.

    - WAITING_FOR_DEPOSIT: 가상계좌 결제 흐름에만 있는 상태입니다. 발급된 가상계좌에 구매자가 아직 입금하지 않은 상태입니다.

    - DONE: 인증된 결제수단으로 요청한 결제가 승인된 상태입니다.

    - CANCELED: 승인된 결제가 취소된 상태입니다.

    - PARTIAL_CANCELED: 승인된 결제가 부분 취소된 상태입니다.

    - ABORTED: 결제 승인이 실패한 상태입니다.

    - EXPIRED: 결제 유효 시간 30분이 지나 거래가 취소된 상태입니다. IN_PROGRESS 상태에서 결제 승인 API를 호출하지 않으면 EXPIRED가 됩니다.
    */
    private String status;

    private ZonedDateTime requestedAt;              // 결제가 일어난 날짜와 시간 정보입니다. yyyy-MM-dd'T'HH:mm:ss±hh:mm ex)2022-01-01T00:00:00+09:00
    private ZonedDateTime approvedAt;               // 결제 승인이 일어난 날짜와 시간 정보입니다. yyyy-MM-dd'T'HH:mm:ss±hh:mm ex)2022-01-01T00:00:00+09:00

    // 기타 정보
    private Boolean useEscrow;                      // 에스크로 사용 여부입니다.
    private Boolean cultureExpense;                 // 문화비(도서, 공연 티켓, 박물관·미술관 입장권 등) 지출 여부입니다. 계좌이체, 가상계좌 결제에만 적용됩니다.
    private String lastTransactionKey;              // 마지막 거래의 키값입니다. 한 결제 건의 승인 거래와 취소 거래를 구분하는 데 사용됩니다. 예를 들어 결제 승인 후 부분 취소를 두 번 했다면 마지막 부분 취소 거래의 키값이 할당됩니다. 최대 길이는 64자입니다.
    private Boolean isPartialCancelable;            // 부분 취소 가능 여부입니다. 이 값이 false이면 전액 취소만 가능합니다.
    private String country;                         // 결제한 국가입니다. ISO-3166의 두 자리 국가 코드 형식입니다.
    private String secret;                          // 웹훅을 검증하는 최대 50자 값입니다. 가상계좌 웹훅 이벤트 본문으로 돌아온 secret과 같으면 정상적인 웹훅입니다. 결제 상태 웹훅 이벤트로 돌아오는 Payment 객체의 secret과 같으면 정상적인 웹훅입니다.

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
    private Map<String, String> metadata;           // 결제 요청 시 SDK에서 직접 추가할 수 있는 결제 관련 정보입니다. 최대 5개의 키-값(key-value) 쌍입니다. 키는 [ , ] 를 사용하지 않는 최대 40자의 문자열, 값은 최대 500자의 문자열입니다

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