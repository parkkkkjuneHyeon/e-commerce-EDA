
package com.phiworks.OrderService.dto.adress;

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

}
