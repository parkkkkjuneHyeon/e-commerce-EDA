package com.example.PaymentService.repository;

import com.example.PaymentService.entity.PaymentsEntity;
import com.example.PaymentService.type.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.Optional;

public interface PaymentsRepository extends JpaRepository<PaymentsEntity, Long> {

    @Modifying
    @Transactional
    @Query(value =
            "UPDATE payments " +
            "SET deleted_at = :deletedAt " +
                ", payment_status = :paymentStatus " +
            "WHERE payment_key = :paymentKey " +
                    "AND deleted_at IS NULL " +
                    "AND member_id = :memberId",
            nativeQuery = true
    )
    void softDeleteByPaymentKey(
            @Param("memberId") Long memberId,
            @Param("paymentKey") String paymentKey,
            @Param("paymentStatus") PaymentStatus paymentStatus,
            @Param("deletedAt") ZonedDateTime deletedAt
    );

    Optional<PaymentsEntity> findByPaymentKey(String paymentKey);
}
