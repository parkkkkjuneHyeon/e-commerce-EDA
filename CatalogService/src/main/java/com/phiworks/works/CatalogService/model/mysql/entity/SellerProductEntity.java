package com.phiworks.works.CatalogService.model.mysql.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="seller_product", indexes = {
        @Index(name = "idx_seller", columnList = "sellerId")
})
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; //productId

    private Long sellerId;

}
