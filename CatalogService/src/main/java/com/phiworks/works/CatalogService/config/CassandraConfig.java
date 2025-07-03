package com.phiworks.works.CatalogService.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.cassandra.core.convert.CassandraCustomConversions;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Arrays;

@Slf4j
@Configuration
public class CassandraConfig {
    @Value("${spring.cassandra.contact-points}")
    private String contactPoint;

    @Value("${spring.cassandra.keyspace-name}")
    private String keySpace;

    @Value("${spring.cassandra.local-datacenter}")
    private String dataCenter;

    @Value("${spring.cassandra.port}")
    private int port;

    @Bean
    public CassandraCustomConversions cassandraCustomConversions() {
        return new CassandraCustomConversions(Arrays.asList(
                new ZonedDateTimeToInstantConverter(),
                new InstantToZonedDateTimeConverter()
        ));
    }

    @WritingConverter
    static class ZonedDateTimeToInstantConverter implements Converter<ZonedDateTime, Instant> {
        @Override
        public Instant convert(ZonedDateTime source) {
            return source.toInstant();
        }
    }

    @ReadingConverter
    static class InstantToZonedDateTimeConverter implements Converter<Instant, ZonedDateTime> {
        @Override
        public ZonedDateTime convert(Instant source) {
            return source.atZone(ZoneOffset.UTC);
        }
    }
}
