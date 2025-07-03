package com.phiworks.works.CatalogService.feignclient;

import com.phiworks.works.CatalogService.feignclient.dto.SearchServiceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "searchClient", url = "http://search-service:8080")
public interface SearchClient {

    @RequestMapping(method = RequestMethod.POST, value = "/search/tags/register")
    void registerProduct(@RequestBody SearchServiceDTO searchServiceDTO);

    @RequestMapping(method = RequestMethod.DELETE, value = "/search/tags")
    void deleteProduct(@RequestBody SearchServiceDTO searchServiceDTO);
}
