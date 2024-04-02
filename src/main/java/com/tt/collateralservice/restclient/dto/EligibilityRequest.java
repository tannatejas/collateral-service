package com.tt.collateralservice.restclient.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EligibilityRequest {
    private List<String> accountId;

    private List<String> assetId;

    @Override
    public String toString() {
        return "EligibilityRequest{" +
                "accountIds=" + accountId +
                ", assetIds=" + assetId +
                '}';
    }

}
