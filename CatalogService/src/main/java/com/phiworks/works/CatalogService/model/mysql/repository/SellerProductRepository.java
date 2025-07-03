package com.phiworks.works.CatalogService.model.mysql.repository;

import com.phiworks.works.CatalogService.model.mysql.entity.SellerProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SellerProductRepository extends JpaRepository<SellerProductEntity, Long> {

    List<SellerProductEntity> findBySellerId(Long sellerId);
}
