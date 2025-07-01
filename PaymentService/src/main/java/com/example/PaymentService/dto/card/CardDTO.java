package com.example.PaymentService.dto.card;


import lombok.*;

/**
 * 카드 결제 정보
 */
/*
card nullable · object
카드로 결제하면 제공되는 카드 관련 정보입니다.

amount number
카드사에 결제 요청한 금액입니다. 즉시 할인 금액(discount.amount)이 포함됩니다.

* 간편결제에 등록된 카드로 결제했다면 간편결제 응답 확인 가이드를 참고하세요.

issuerCode string
카드 발급사 두 자리 코드입니다. 카드사 코드를 참고하세요.

acquirerCode nullable · string
카드 매입사 두 자리 코드입니다. 카드사 코드를 참고하세요.

number string
카드번호입니다. 번호의 일부는 마스킹 되어 있습니다. 최대 길이는 20자입니다.

installmentPlanMonths integer
할부 개월 수입니다. 일시불이면 0입니다.

approveNo string
카드사 승인 번호입니다. 최대 길이는 8자입니다.

useCardPoint boolean
카드사 포인트 사용 여부입니다.

* 일반 카드사 포인트가 아닌, 특수한 포인트나 바우처를 사용하면 할부 개월 수가 변경되어 응답이 돌아오니 유의해주세요.

cardType string
카드 종류입니다. 신용, 체크, 기프트, 미확인 중 하나입니다. 구매자가 해외 카드로 결제했거나 간편결제의 결제 수단을 조합해서 결제했을 때 미확인으로 표시됩니다.

ownerType string
카드의 소유자 타입입니다. 개인, 법인, 미확인 중 하나입니다. 구매자가 해외 카드로 결제했거나 간편결제의 결제 수단을 조합해서 결제했을 때 미확인으로 표시됩니다.

acquireStatus string
카드 결제의 매입 상태입니다. 아래와 같은 상태 값을 가질 수 있습니다.

- READY: 아직 매입 요청이 안 된 상태입니다.

- REQUESTED: 매입이 요청된 상태입니다.

- COMPLETED: 요청된 매입이 완료된 상태입니다.

- CANCEL_REQUESTED: 매입 취소가 요청된 상태입니다.

- CANCELED: 요청된 매입 취소가 완료된 상태입니다.

isInterestFree boolean
무이자 할부의 적용 여부입니다.

interestPayer nullable · stringv1.4
할부가 적용된 결제에서 할부 수수료를 부담하는 주체입니다. BUYER, CARD_COMPANY, MERCHANT 중 하나입니다.

- BUYER: 상품을 구매한 구매자가 할부 수수료를 부담합니다. 일반적인 할부 결제입니다.

- CARD_COMPANY: 카드사에서 할부 수수료를 부담합니다. 무이자 할부 결제입니다.

- MERCHANT: 상점에서 할부 수수료를 부담합니다. 무이자 할부 결제입니다.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDTO {
    private Long amount;
    private String issuerCode;
    private String acquirerCode;
    private String number;
    private Integer installmentPlanMonths;
    private String approveNo;
    private Boolean useCardPoint;
    private String cardType;
    private String ownerType;
    private String acquireStatus;
    private Boolean isInterestFree;
    private String interestPayer;
}
