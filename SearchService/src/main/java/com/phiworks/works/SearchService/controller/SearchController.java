package com.phiworks.works.SearchService.controller;

import com.phiworks.works.SearchService.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

//    @PostMapping("/tags/register")
//    public ResponseEntity<Long> registerTag(
//            @RequestBody SearchRequestDTO searchRequestDTO) {
//        Long productId = searchService.addTagCache(searchRequestDTO);
//
//        return ResponseEntity.ok(productId);
//    }
//
//    @DeleteMapping("/tags")
//    public ResponseEntity<HttpStatus> deleteTag(
//            @RequestBody SearchRequestDTO searchRequestDTO) {
//        searchService.removeTagCache(searchRequestDTO);
//
//        return ResponseEntity.ok(HttpStatus.OK);
//    }

    @GetMapping("/tags/{tag}")
    public ResponseEntity<List<Long>> getTags(@PathVariable String tag) {
        List<Long> ProductIds = searchService.getTags(tag);

        return ResponseEntity.ok(ProductIds);
    }

}
