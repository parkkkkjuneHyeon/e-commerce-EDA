package com.phiworks.works.CatalogService.model.cassandra.entity;


import lombok.*;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;
import java.time.ZonedDateTime;
import java.util.List;


@Table(keyspace = "catalog")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity {
    @PrimaryKey
    private Long id;

    @Column
    private Long sellerId;

    @Column
    private String productName;

    @Column
    private String productDescription;

    @Column
    private Long productPrice;

    @Column
    private Long stockCount;

    @Column
    private List<String> tags;

    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    @Column
    private ZonedDateTime createdAt;

    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    @Column
    private ZonedDateTime updatedAt;

    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    @Column
    private ZonedDateTime deletedAt;
}
