package com.phiworks.works.SearchService.service;

import com.phiworks.works.SearchService.dto.SearchRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final RedisTemplate<String, String> redisTemplate;


    public Long addTagCache(SearchRequestDTO searchRequestDTO) {
        var ops = redisTemplate.opsForSet();
        List<String> tags = searchRequestDTO.getTags();
        tags.forEach(tag -> {
            ops.add(tag, searchRequestDTO.getProductId().toString());
        });

        return searchRequestDTO.getProductId();
    }

    public void removeTagCache(SearchRequestDTO searchRequestDTO) {
        var ops = redisTemplate.opsForSet();
        List<String> tags = searchRequestDTO.getTags();
        tags.forEach(tag -> {
           ops.remove(tag, searchRequestDTO.getProductId().toString());
        });
    }

    public List<Long> getTags(String tag) {
        var ops = redisTemplate.opsForSet();
        var values = ops.members(tag);
        if (values != null) {
            return values.stream().map(Long::parseLong).toList();
        }
        return Collections.emptyList();
    }
}
