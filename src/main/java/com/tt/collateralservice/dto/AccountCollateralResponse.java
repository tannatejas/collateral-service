package com.tt.collateralservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountCollateralResponse {
    private String accountId;
    private BigDecimal collateralValue;

    @Override
    public String toString() {
        return "AccountCollateralResponse{" +
                "accountId='" + accountId + '\'' +
                ", collateralValue=" + collateralValue +
                '}';
    }
}
