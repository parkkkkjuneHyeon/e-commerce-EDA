package com.phiworks.works.SearchService.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequestDTO {
    private List<String> tags;
    private Long productId;
}
