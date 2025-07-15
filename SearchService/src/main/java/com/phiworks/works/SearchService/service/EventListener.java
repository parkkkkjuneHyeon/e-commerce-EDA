package com.phiworks.works.SearchService.service;

import com.google.protobuf.InvalidProtocolBufferException;
import com.phiworks.works.SearchService.dto.SearchRequestDTO;
import edaordersystem.protobuf.EdaMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventListener {
    private final SearchService searchService;

    @KafkaListener(topics = "product_tags_added")
    public void consumeTagAdded(byte[] message) throws InvalidProtocolBufferException {
        var object = EdaMessage.ProductTags.parseFrom(message);

        log.info("[product_tags_added] : {} ", object);

        SearchRequestDTO searchRequestDTO = SearchRequestDTO.builder()
                .productId(object.getProductId())
                .tags(object.getTagsList())
                .build();

        searchService.addTagCache(searchRequestDTO);
    }

    @KafkaListener(topics = "product_tags_removed")
    public void consumeTagRemoved(byte[] message) throws InvalidProtocolBufferException {
        var object = EdaMessage.ProductTags.parseFrom(message);

        log.info("[product_tags_removed] : {} ", object);

        var searchRequestDTO = SearchRequestDTO.builder()
                .productId(object.getProductId())
                .tags(object.getTagsList())
                .build();

        searchService.removeTagCache(searchRequestDTO);
    }
}
