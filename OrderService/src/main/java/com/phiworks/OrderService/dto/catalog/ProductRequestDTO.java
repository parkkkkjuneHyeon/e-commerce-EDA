package com.phiworks.OrderService.dto.catalog;


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
}
