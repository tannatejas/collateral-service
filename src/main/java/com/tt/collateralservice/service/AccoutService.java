package com.tt.collateralservice.service;

import com.tt.collateralservice.dto.*;
import com.tt.collateralservice.repository.AccountRepository;
import com.tt.collateralservice.restclient.client.EligibilityServiceClient;
import com.tt.collateralservice.restclient.client.PositionServiceClient;
import com.tt.collateralservice.restclient.client.PriceServiceClient;
import com.tt.collateralservice.restclient.dto.EligibilityResponse;
import com.tt.collateralservice.restclient.dto.PositionResponse;
import com.tt.collateralservice.restclient.dto.PriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RefreshScope
public class AccoutService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PositionServiceClient positionServiceClient;
    @Autowired
    EligibilityServiceClient eligibilityServiceClient;

    @Autowired
    PriceServiceClient priceServiceClient;
    public List<AccountCollateralResponse> getAccountCollateral(final List<String> accounts) {

        List<PositionResponse> positions = positionServiceClient.fetchPositions(accounts);
        System.out.println(positions.toString());
        Map<String, Integer> assets = positionServiceClient.parsePositionResponse(positions);

        List<String> assetId = new ArrayList<>(assets.keySet());
        List<EligibilityResponse> eligibilityResponses = eligibilityServiceClient.fetchEligibility(accounts, assetId);

        List<PriceResponse> priceResponses = priceServiceClient.fetchPrices(assetId);

        Map<String, BigDecimal> assetPriceMap = new HashMap<>();
        for (PriceResponse price : priceResponses) {
            assetPriceMap.put(price.getAssetId(), BigDecimal.valueOf(price.getPrice()));
        }
        List<AccountCollateralResponse> accountCollaterals = new ArrayList<>();
        //#Todo - To Simplify/Improve
        for (String accountId : accounts) {
            BigDecimal totalCollateral = BigDecimal.ZERO;
            for (PositionResponse positionResponse : positions) {
                if (positionResponse.getAccountId().equals(accountId)) {
                    for (Position position : positionResponse.getPositions()) {
                        EligibilityResponse eligibilityResponse = eligibilityServiceClient.findEligibility(eligibilityResponses, accountId, position.getAssetId());
                        System.out.println(eligibilityResponse.toString());
                        BigDecimal assetPrice = assetPriceMap.getOrDefault(position.getAssetId(), BigDecimal.ZERO);
                        BigDecimal collateral = calculateCollateral(position, eligibilityResponse, assetPrice);
                        totalCollateral = totalCollateral.add(collateral);
                    }
                }
            }
            accountCollaterals.add(new AccountCollateralResponse(accountId, totalCollateral.setScale(2, RoundingMode.HALF_UP)));
        }
        return accountCollaterals;
    }



    private BigDecimal calculateCollateral(final Position position, final EligibilityResponse eligibility, final BigDecimal assetPrice) {
        if (eligibility != null && eligibility.isEligible()) {
            BigDecimal quantity = BigDecimal.valueOf(position.getQuantity());
            BigDecimal discountFactor = BigDecimal.valueOf(eligibility.getDiscount());
            return quantity.multiply(assetPrice).multiply(discountFactor);
        } else {
            return BigDecimal.ZERO;
        }
    }
}
