package com.example.PaymentService.repository;

import com.example.PaymentService.entity.PaymentsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;

public interface PaymentsRepository extends JpaRepository<PaymentsEntity, Long> {

    @Modifying
    @Transactional
    @Query(value =
            "UPDATE payments " +
            "SET deleted_at = :deletedAt " +
            "WHERE payment_key = :paymentKey " +
                    "AND deleted_at IS NULL " +
                    "AND member_id = :memberId",
            nativeQuery = true
    )
    void softDeleteByPaymentKey(
            @Param("memberId") Long memberId,
            @Param("paymentKey") String paymentKey,
            @Param("deletedAt") ZonedDateTime deletedAt
    );
}
