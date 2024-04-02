package com.tt.collateralservice.restclient.client;

import com.tt.collateralservice.exception.ServiceUnAvailableException;
import io.github.resilience4j.retry.annotation.Retry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ServiceClientImpl implements  ServiceClient{

    @Autowired
    @Lazy
    RestTemplate restTemplate;

    private static final Logger logger = LoggerFactory.getLogger(ServiceClientImpl.class);

    @Override
    @Retry(name="collateralService")
    public <T> List<T> fetchPostForEntity(String endpoint, Map<String, List<String>> requestBody, Class<T[]> responseType) {

        logger.info(String.format("Calling Service URL: %s", endpoint));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, List<String>>> requestEntity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<T[]> responseEntity = restTemplate.postForEntity(endpoint, requestEntity, responseType);
            T[] responseData = responseEntity.getBody();
            return responseData != null ? List.of(responseData) : new ArrayList<>();
        } catch (HttpClientErrorException | HttpServerErrorException | IllegalStateException ex) {
            throw new ServiceUnAvailableException(String.format("Error Calling Service: %s", endpoint));
        }
    }
}
