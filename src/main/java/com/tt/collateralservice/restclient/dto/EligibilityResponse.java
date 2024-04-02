package com.tt.collateralservice.restclient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EligibilityResponse {

    private boolean eligible;
    private List<String> assetId;
    private List<String> accountId;
    private double discount;

    @Override
    public String toString() {
        return "EligibilityResponse{" +
                "eligible=" + eligible +
                ", assetIds=" + assetId +
                ", accountIds=" + accountId +
                ", discount=" + discount +
                '}';
    }
}
