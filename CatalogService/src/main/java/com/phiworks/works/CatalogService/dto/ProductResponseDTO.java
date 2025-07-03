package com.phiworks.works.CatalogService.dto;


import com.phiworks.works.CatalogService.model.cassandra.entity.ProductEntity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponseDTO {
    private Long productId;

    private Long sellerId;

    private String productName;

    private String productDescription;

    private Long productPrice;

    private Long stockCount;

    private List<String> tags;

    public static ProductResponseDTO getProductResponseDTO(
            ProductEntity productEntity) {
        return ProductResponseDTO.builder()
                .productId(productEntity.getId())
                .sellerId(productEntity.getSellerId())
                .productName(productEntity.getProductName())
                .productDescription(productEntity.getProductDescription())
                .productPrice(productEntity.getProductPrice())
                .stockCount(productEntity.getStockCount())
                .tags(productEntity.getTags())
                .build();
    }
}
