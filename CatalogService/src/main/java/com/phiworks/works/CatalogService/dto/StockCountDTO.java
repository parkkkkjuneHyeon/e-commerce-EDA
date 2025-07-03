package com.phiworks.works.CatalogService.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StockCountDTO {
    private Long incrementStockCount;
    private Long decrementStockCount;

}
