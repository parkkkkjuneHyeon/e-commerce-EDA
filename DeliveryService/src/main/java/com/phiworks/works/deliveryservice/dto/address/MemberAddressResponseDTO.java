package com.phiworks.works.deliveryservice.dto.address;

import com.phiworks.works.deliveryservice.entity.MemberAddressEntity;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberAddressResponseDTO {

    private Long id;

    private Long memberId;

    private String address;

    private String alias;


    public static MemberAddressResponseDTO of(MemberAddressEntity memberAddressEntity) {
        return MemberAddressResponseDTO.builder()
                .id(memberAddressEntity.getId())
                .memberId(memberAddressEntity.getMemberId())
                .address(memberAddressEntity.getAddress())
                .alias(memberAddressEntity.getAlias())
                .build();
    }
}
