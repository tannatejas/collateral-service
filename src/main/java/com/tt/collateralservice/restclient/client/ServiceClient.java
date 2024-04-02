package com.tt.collateralservice.restclient.client;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ServiceClient {
    public <T> List<T> fetchPostForEntity(String endpoint, Map<String, List<String>> requestBody, Class<T[]> responseType);
}
