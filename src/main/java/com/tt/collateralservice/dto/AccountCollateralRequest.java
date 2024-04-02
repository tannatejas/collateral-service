package com.tt.collateralservice.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountCollateralRequest {

    @NotEmpty(message = "Accounts list must not be empty")
    private List<String> accounts;

    @Override
    public String toString() {
        return "AccountCollateralRequest{" +
                "accounts=" + accounts +
                '}';
    }

}
