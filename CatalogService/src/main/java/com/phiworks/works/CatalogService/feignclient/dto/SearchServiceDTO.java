package com.phiworks.works.CatalogService.feignclient.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchServiceDTO {
    private List<String> tags;
    private Long productId;
}
