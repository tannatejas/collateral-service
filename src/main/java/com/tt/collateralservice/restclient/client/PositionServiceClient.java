package com.tt.collateralservice.restclient.client;

import com.tt.collateralservice.dto.Position;
import com.tt.collateralservice.restclient.dto.PositionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PositionServiceClient {

    @Value("${microservice.position-service.endpoints.endpoint.uri}")
    private String POSITION_SERVICE_ENDPOINT;

    @Autowired
    ServiceClientImpl serviceClient;

    public List<PositionResponse> fetchPositions(List<String> accounts) {
        Map<String, List<String>> requestBody = createRequestBody(accounts, null);
        return serviceClient.fetchPostForEntity(POSITION_SERVICE_ENDPOINT, requestBody, PositionResponse[].class);
    }

    private Map<String, List<String>> createRequestBody(List<String> accounts, List<String> assets) {
        Map<String, List<String>> requestBody = new HashMap<>();
        requestBody.put("accountId", accounts);
        return requestBody;
    }

    public Map<String, Integer> parsePositionResponse(final List<PositionResponse> positionResponses) {
        Map<String, Integer> assets = new HashMap<>();
        for (PositionResponse positionResponse : positionResponses) {
            List<Position> positions = positionResponse.getPositions();
            if (positions != null) {
                for (Position position : positions) {
                    assets.put(position.getAssetId(), position.getQuantity());
                }
            }
        }
        return assets;
    }
}
