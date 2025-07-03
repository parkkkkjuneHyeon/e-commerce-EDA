package com.phiworks.works.CatalogService.dto;


import com.phiworks.works.CatalogService.model.cassandra.entity.ProductEntity;
import com.phiworks.works.CatalogService.model.mysql.entity.SellerProductEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDTO {
    private Long productId;

    private Long sellerId;

    private String productName;

    private String productDescription;

    private Long productPrice;

    private Long stockCount;

    private List<String> tags;

    public static ProductEntity getProductEntity(ProductRequestDTO productRequestDTO) {
        return ProductEntity.builder()
                .id(productRequestDTO.getProductId())
                .sellerId(productRequestDTO.getSellerId())
                .productName(productRequestDTO.getProductName())
                .productDescription(productRequestDTO.getProductDescription())
                .productPrice(productRequestDTO.getProductPrice())
                .stockCount(productRequestDTO.getStockCount())
                .tags(productRequestDTO.getTags())
                .build();
    }
    public static SellerProductEntity getSellerProductEntity(ProductRequestDTO productRequestDTO) {
        return SellerProductEntity.builder()
                .id(productRequestDTO.getProductId())
                .sellerId(productRequestDTO.getSellerId())
                .build();
    }
}
