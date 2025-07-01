package com.phiworks.works.deliveryservice.service;

import com.phiworks.works.deliveryservice.dg.DeliveryStrategy;
import com.phiworks.works.deliveryservice.dg.adapter.DeliveryAdapter;
import com.phiworks.works.deliveryservice.dto.address.MemberAddressRequestDTO;
import com.phiworks.works.deliveryservice.dto.address.MemberAddressResponseDTO;
import com.phiworks.works.deliveryservice.dto.delivery.DeliveryRequestDTO;
import com.phiworks.works.deliveryservice.dto.delivery.DeliveryResponseDTO;
import com.phiworks.works.deliveryservice.entity.DeliveryEntity;
import com.phiworks.works.deliveryservice.entity.MemberAddressEntity;
import com.phiworks.works.deliveryservice.repository.DeliveryRepository;
import com.phiworks.works.deliveryservice.repository.MemberAddressRepository;
import com.phiworks.works.deliveryservice.type.DeliveryStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    private final MemberAddressRepository memberAddressRepository;
    private final DeliveryRepository deliveryRepository;
    private final DeliveryStrategy deliveryStrategy;


    public MemberAddressResponseDTO registerAddress(MemberAddressRequestDTO memberAddressRequestDTO) {
        MemberAddressEntity memberAddressEntity = MemberAddressRequestDTO
                .of(memberAddressRequestDTO);

        MemberAddressEntity registerMemberAddress = memberAddressRepository
                .save(memberAddressEntity);

        return MemberAddressResponseDTO.of(registerMemberAddress);
    }

    public DeliveryResponseDTO processDelivery(DeliveryRequestDTO deliveryRequestDTO) {
        //외부 api 요청
        Long referenceCode = deliveryStrategy.processDelivery(deliveryRequestDTO);
        deliveryRequestDTO.setReferenceCode(referenceCode);

        DeliveryEntity deliveryEntity = DeliveryRequestDTO
                .of(deliveryRequestDTO, DeliveryStatus.REQUESTED);

        DeliveryEntity savedDeliveryEntity = deliveryRepository.save(deliveryEntity);

        return DeliveryResponseDTO.of(savedDeliveryEntity);
    }

    public MemberAddressResponseDTO getAddress(Long addressId) {
        MemberAddressEntity memberAddressEntity = memberAddressRepository
                .findById(addressId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid address id: " + addressId));

        return MemberAddressResponseDTO.of(memberAddressEntity);
    }

    public DeliveryResponseDTO getDelivery(Long deliveryId) {
        DeliveryEntity deliveryEntity = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid delivery id: " + deliveryId));

        return DeliveryResponseDTO.of(deliveryEntity);
    }


    public List<MemberAddressResponseDTO> getMemberAddress(Long memberId) {
        return memberAddressRepository.findByMemberId(memberId)
                .stream().map(MemberAddressResponseDTO::of)
                .toList();

    }

    public DeliveryResponseDTO getDeliveryByOrderId(String orderId) {
        return deliveryRepository.findByOrderId(orderId)
                .map(DeliveryResponseDTO::of)
                .orElseThrow(() -> new RuntimeException("해당 주문건이 존재 하지않습니다."));

    }
}
