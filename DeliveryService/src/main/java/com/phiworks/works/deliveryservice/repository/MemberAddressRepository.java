package com.phiworks.works.deliveryservice.repository;

import com.phiworks.works.deliveryservice.entity.MemberAddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberAddressRepository extends JpaRepository<MemberAddressEntity, Long> {

    List<MemberAddressEntity> findByMemberId(Long memberId);
}
