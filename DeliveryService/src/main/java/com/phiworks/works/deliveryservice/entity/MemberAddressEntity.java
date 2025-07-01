package com.phiworks.works.deliveryservice.entity;


import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@Entity
@Table(name = "member_address", indexes = {
        @Index(name = "idx_member_id", columnList = "memberId")
})
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberAddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;

    private String address;

    private String alias; //주소에 대한 별칭 //집, 회사 등등
}
