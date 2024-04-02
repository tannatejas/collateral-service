package com.tt.collateralservice.restclient.client;

import com.tt.collateralservice.restclient.dto.PriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PriceServiceClient {

    @Autowired
    ServiceClientImpl serviceClient;

    @Value("${microservice.price-service.endpoints.endpoint.uri}")
    private String PRICE_SERVICE_ENDPOINT;

    public List<PriceResponse> fetchPrices(List<String> assets) {
        Map<String, List<String>> requestBody = createRequestBody(null, assets);
        return serviceClient.fetchPostForEntity(PRICE_SERVICE_ENDPOINT, requestBody, PriceResponse[].class);
    }

    private Map<String, List<String>> createRequestBody(List<String> accounts, List<String> assets) {
        Map<String, List<String>> requestBody = new HashMap<>();
        requestBody.put("assetId", assets);
        return requestBody;
    }
}
