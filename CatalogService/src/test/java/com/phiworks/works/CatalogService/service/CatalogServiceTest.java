package com.phiworks.works.CatalogService.service;

import com.phiworks.works.CatalogService.dto.ProductRequestDTO;
import com.phiworks.works.CatalogService.dto.StockCountDTO;
import com.phiworks.works.CatalogService.model.cassandra.entity.ProductEntity;
import com.phiworks.works.CatalogService.model.cassandra.repository.ProductRepository;
import com.phiworks.works.CatalogService.model.mysql.entity.SellerProductEntity;
import com.phiworks.works.CatalogService.model.mysql.repository.SellerProductRepository;
import edaordersystem.protobuf.EdaMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CatalogServiceTest {

    @Mock
    private KafkaTemplate<String, byte[]> kafkaTemplate;
    @Mock
    private ProductRepository productRepository;

    @Mock
    private SellerProductRepository sellerProductRepository;

    @InjectMocks
    private CatalogService catalogService;

    StockCountDTO stockCountDTO;
    ProductRequestDTO productRequestDTO;
    ProductEntity mockProductEntity;
    Long productCount = 10L;

    SellerProductEntity sellerProductEntity;


    @BeforeEach
    void setUp() {
        stockCountDTO = StockCountDTO.builder()
                .decrementStockCount(3L)
                .build();

        mockProductEntity = ProductEntity.builder()
                .id(1L)
                .stockCount(productCount)
                .productName("이어폰")
                .productDescription("성능이 굿")
                .productPrice(100000L)
                .sellerId(123L)
                .tags(List.of("무선","블루투스","이어폰","전자제품"))
                .build();

        productRequestDTO = ProductRequestDTO.builder()
                .productId(1L)
                .productName("이어폰")
                .productDescription("성능이 굿")
                .productPrice(100000L)
                .sellerId(123L)
                .stockCount(productCount)
                .tags(List.of("무선","블루투스","이어폰","전자제품"))
                .build();

        sellerProductEntity = SellerProductEntity.builder()
                .id(1L)
                .sellerId(123L)
                .build();
    }

    @Test
    void decreaseStockCount_상품수량이_감소한다() {
        //give
        Long productId = 1L;
        
        when(productRepository.findById(productId))
                .thenReturn(Optional.ofNullable(mockProductEntity));

        when(productRepository.save(any(ProductEntity.class)))
                .thenReturn(mockProductEntity);

        //when
        var productResponseDTO = catalogService.decreaseStockCount(productId, stockCountDTO);

        //then
        verify(productRepository, times(1)).findById(any(Long.class));
        verify(productRepository, times(1)).save(any(ProductEntity.class));

        assertThat(productResponseDTO.getProductId()).isEqualTo(productId);
        assertThat(productResponseDTO.getStockCount()).isEqualTo(productCount-stockCountDTO.getDecrementStockCount());

    }

    @Test
    void registerProduct() {
        //give
        var productTags = EdaMessage.ProductTags.newBuilder()
                .setProductId(1L)
                .addAllTags(productRequestDTO.getTags())
                .build();

        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<byte[]> valueCaptor = ArgumentCaptor.forClass(byte[].class);

        //when
        when(sellerProductRepository.save(any(SellerProductEntity.class)))
                .thenReturn(sellerProductEntity);

        when(productRepository.save(any(ProductEntity.class)))
                .thenReturn(mockProductEntity);

        verify(kafkaTemplate, times(1))
                .send(keyCaptor.capture(), valueCaptor.capture());

        var productResponseDTO = catalogService.registerProduct(productRequestDTO);


        //then

        //kafka 해당 토픽으로 해당 데이터를 보냈는지
        var key = keyCaptor.getValue();
        var value = valueCaptor.getValue();
        assertThat(key.toString()).isEqualTo("product_tags_added");
        assertThat(value.toString()).isEqualTo(productTags.toString());

        //상품을 제대로 저장이 되었는지
        assertThat(productResponseDTO.getProductId())
                .isEqualTo(productRequestDTO.getProductId());
        assertThat(productResponseDTO.getProductName())
                .isEqualTo(productRequestDTO.getProductName());
        assertThat(productResponseDTO.getProductDescription())
                .isEqualTo(productRequestDTO.getProductDescription());
        assertThat(productResponseDTO.getProductPrice())
                .isEqualTo(productRequestDTO.getProductPrice());
        assertThat(productResponseDTO.getSellerId())
                .isEqualTo(productRequestDTO.getSellerId());
        assertThat(productResponseDTO.getStockCount())
                .isEqualTo(productRequestDTO.getStockCount());
        assertThat(productResponseDTO.getTags())
                .isEqualTo(productRequestDTO.getTags());

    }

    @Test
    void deleteProduct() {
    }
}