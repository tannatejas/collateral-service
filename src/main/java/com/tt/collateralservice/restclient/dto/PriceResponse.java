package com.tt.collateralservice.restclient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PriceResponse {
    private String assetId;

    @Override
    public String toString() {
        return "PriceResponse{" +
                "assetId='" + assetId + '\'' +
                ", price=" + price +
                '}';
    }

    private Double price;
}
