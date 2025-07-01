package com.example.PaymentService.dto;


import com.example.PaymentService.dto.virtualaccount.VirtualAccountDTO.RefundReceiveAccount;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentCancelDTO {

    @NotNull
    private Long memberId;
    @NotNull
    private String paymentKey;
    @NotNull
    @Max(value = 200)
    private String cancelReason;                                                // 구매 취소 사유
    private String cancelAmount;
    private RefundReceiveAccount refundReceiveAccount;
    private String taxFreeAmount;
    private String currency;
}
