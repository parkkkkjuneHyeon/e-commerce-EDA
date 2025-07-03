package com.phiworks.works.CatalogService.model.cassandra.repository;

import com.phiworks.works.CatalogService.model.cassandra.entity.ProductEntity;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CassandraRepository<ProductEntity, Long> {
}
