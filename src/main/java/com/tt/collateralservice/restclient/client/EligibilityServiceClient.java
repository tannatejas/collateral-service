package com.tt.collateralservice.restclient.client;

import com.tt.collateralservice.restclient.dto.EligibilityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EligibilityServiceClient {

    @Autowired
    ServiceClientImpl serviceClient;

    @Value("${microservice.eligibility-service.endpoints.endpoint.uri}")
    private String ELIGIBILITY_SERVICE_ENDPOINT;


    public List<EligibilityResponse> fetchEligibility(List<String> accounts, List<String> assets) {
        Map<String, List<String>> requestBody = createRequestBody(accounts, assets);
        return serviceClient.fetchPostForEntity(ELIGIBILITY_SERVICE_ENDPOINT, requestBody, EligibilityResponse[].class);
    }

    private Map<String, List<String>> createRequestBody(List<String> accounts, List<String> assets) {
        Map<String, List<String>> requestBody = new HashMap<>();
        requestBody.put("accounts", accounts);
        requestBody.put("assetId", assets);
        return requestBody;
    }

    public EligibilityResponse findEligibility(final List<EligibilityResponse> eligibilities, final String accountId, final String assetId) {
        for (EligibilityResponse eligibility : eligibilities) {
            if (eligibility.getAccountId().contains(accountId) && eligibility.getAssetId().contains(assetId)) {
                return eligibility;
            }
        }
        return null;
    }
}
