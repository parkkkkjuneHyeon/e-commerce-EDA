package com.phiworks.works.CatalogService.common.util;

public class StockCountManager {

    public static Long incrementStockCount(Long productStockCount, Long incrementStockCount) {
        return productStockCount + incrementStockCount;
    }

    public static Long decrementStockCount(Long productStockCount, Long decrementStockCount) {

        Long resultCount = productStockCount - decrementStockCount;

        return resultCount < 0 ? 0 : resultCount;
    }
}
