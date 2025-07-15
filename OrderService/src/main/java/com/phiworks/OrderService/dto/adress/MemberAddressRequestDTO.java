package com.phiworks.OrderService.dto.adress;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberAddressRequestDTO {

    @NotNull
    private Long memberId;
    @NotNull
    private String address;
    @NotNull
    private String alias; //주소에 대한 별칭 //집, 회사 등등



}
