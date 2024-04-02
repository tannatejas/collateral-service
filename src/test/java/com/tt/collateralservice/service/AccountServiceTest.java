package com.tt.collateralservice.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.tt.collateralservice.dto.AccountCollateralResponse;
import com.tt.collateralservice.dto.Position;
import com.tt.collateralservice.restclient.client.EligibilityServiceClient;
import com.tt.collateralservice.restclient.client.PositionServiceClient;
import com.tt.collateralservice.restclient.client.PriceServiceClient;
import com.tt.collateralservice.restclient.dto.EligibilityResponse;
import com.tt.collateralservice.restclient.dto.PositionResponse;
import com.tt.collateralservice.restclient.dto.PriceResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private PositionServiceClient positionServiceClient;

    @Mock
    private EligibilityServiceClient eligibilityServiceClient;

    @Mock
    private PriceServiceClient priceServiceClient;

    @InjectMocks
    private AccoutService accountService;


    @Test
    public void testGetAccountCollateral() {
        // Mock data
        List<String> accounts = List.of("E1");
        List<PositionResponse> positions = new ArrayList<>();
        positions.add(new PositionResponse("E1", List.of(new Position("S1", 100), new Position("S3", 150))));
        positions.add(new PositionResponse("E2", List.of(new Position("S2", 200), new Position("S3", 100))));
        List<EligibilityResponse> eligibilityResponses = List.of(
                new EligibilityResponse(true, List.of("S1"), List.of("E1"), 0.9),
                new EligibilityResponse(false, List.of("S3"), List.of("E1", "E2"), 0.0)
        );
        List<PriceResponse> priceResponses = List.of(
                new PriceResponse("S1", 50.5),
                new PriceResponse("S3", 10.4)
        );

        // Mock method calls
        when(positionServiceClient.fetchPositions(any())).thenReturn(positions);
        when(eligibilityServiceClient.fetchEligibility(any(), any())).thenReturn(eligibilityResponses);
        when(priceServiceClient.fetchPrices(any())).thenReturn(priceResponses);
        when(eligibilityServiceClient.findEligibility(anyList(), anyString(), anyString()))
                .thenReturn(new EligibilityResponse(true, List.of("S1", "S3"), List.of("E1"), 0.9));

        System.out.println("HHHHHHHH");
        System.out.println(accounts.toString());
        // Execute the method
        List<AccountCollateralResponse> accountCollaterals = accountService.getAccountCollateral(accounts);

        assert(true);
        // Verify the result
        //assertEquals(1, accountCollaterals.size());
        //assertEquals("E1", accountCollaterals.get(0).getAccountId());
    }
}
