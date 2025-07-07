package com.phiworks.works.CatalogService.service;


import com.phiworks.works.CatalogService.common.util.CurrentTimeUtil;
import com.phiworks.works.CatalogService.common.util.StockCountManager;
import com.phiworks.works.CatalogService.dto.ProductRequestDTO;
import com.phiworks.works.CatalogService.dto.ProductResponseDTO;
import com.phiworks.works.CatalogService.dto.StockCountDTO;
import com.phiworks.works.CatalogService.model.cassandra.entity.ProductEntity;
import com.phiworks.works.CatalogService.model.cassandra.repository.ProductRepository;
import com.phiworks.works.CatalogService.model.mysql.repository.SellerProductRepository;
import edaordersystem.protobuf.EdaMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private static final Logger log = LoggerFactory.getLogger(CatalogService.class);
    private final ProductRepository productRepository;
    private final SellerProductRepository sellerProductRepository;
    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    @Transactional
    public ProductResponseDTO registerProduct(
            ProductRequestDTO productRequestDTO
    ) {
        var SellerProductEntity = ProductRequestDTO.getSellerProductEntity(productRequestDTO);
        var savedSellerProductEntity = sellerProductRepository.save(SellerProductEntity);

        var productEntity = ProductRequestDTO.getProductEntity(productRequestDTO);

        productEntity.setId(savedSellerProductEntity.getId());
        productEntity.setCreatedAt(CurrentTimeUtil.getCurrentTime());
        productEntity.setUpdatedAt(CurrentTimeUtil.getCurrentTime());

        var savedProductEntity = productRepository.save(productEntity);


        var message = EdaMessage.ProductTags.newBuilder()
                .setProductId(savedSellerProductEntity.getId())
                .addAllTags(savedProductEntity.getTags())
                .build();
        log.info("[product_tags_added]");
        kafkaTemplate.send("product_tags_added", message.toByteArray());

        return ProductResponseDTO.getProductResponseDTO(savedProductEntity);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        var productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        sellerProductRepository.deleteById(productId);
        productRepository.deleteById(productId);

        var message = EdaMessage.ProductTags.newBuilder()
                .setProductId(productId)
                .addAllTags(productEntity.getTags())
                .build();
        log.info("[product_tags_removed]");
        kafkaTemplate.send("product_tags_removed", message.toByteArray());
    }

    public ProductResponseDTO getProductById(Long productId) {
        var productEntity = productRepository.findById(productId)
                .orElse(new ProductEntity());

        return ProductResponseDTO.getProductResponseDTO(productEntity);
    }

    public List<ProductResponseDTO> getProductBySellerId(Long sellerId) {
        var sellerProductEntityList = sellerProductRepository.findBySellerId(sellerId);

        return  sellerProductEntityList.stream()
                .map(sellerProductEntity -> {
                    var productEntity = productRepository.findById(sellerProductEntity.getId())
                            .orElseThrow(() -> new RuntimeException("Product not found"));

                    return ProductResponseDTO.getProductResponseDTO(productEntity);
                }).toList();
    }

    @Transactional
    public ProductResponseDTO decreaseStockCount(Long productId, StockCountDTO stockCountDTO) {
        var productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Long stockCount = StockCountManager.decrementStockCount(
                productEntity.getStockCount(),
                stockCountDTO.getDecrementStockCount()
        );

        productEntity.setStockCount(stockCount);

        productRepository.save(productEntity);

        return ProductResponseDTO.getProductResponseDTO(productEntity);
    }

    @Transactional
    public ProductResponseDTO increaseStockCount(Long productId, StockCountDTO stockCountDTO) {
        var productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Long stockCount = StockCountManager.incrementStockCount(
                productEntity.getStockCount(),
                stockCountDTO.getIncrementStockCount()
        );

        productEntity.setStockCount(stockCount);

        productRepository.save(productEntity);

        return ProductResponseDTO.getProductResponseDTO(productEntity);
    }

}
