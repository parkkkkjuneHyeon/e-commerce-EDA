package com.phiworks.works.deliveryservice.dto.address;

import com.phiworks.works.deliveryservice.entity.MemberAddressEntity;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

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


    public static MemberAddressEntity of(MemberAddressRequestDTO memberAddressRequestDTO) {
        return MemberAddressEntity.builder()
                .memberId(memberAddressRequestDTO.getMemberId())
                .address(memberAddressRequestDTO.getAddress())
                .alias(memberAddressRequestDTO.getAlias())
                .build();
    }
}
