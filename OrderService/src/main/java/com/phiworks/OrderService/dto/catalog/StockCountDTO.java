package com.phiworks.OrderService.dto.catalog;

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
