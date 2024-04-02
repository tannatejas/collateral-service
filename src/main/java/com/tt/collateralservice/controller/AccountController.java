package com.tt.collateralservice.controller;

import com.tt.collateralservice.dto.AccountCollateralRequest;
import com.tt.collateralservice.dto.AccountCollateralResponse;
import com.tt.collateralservice.entity.Account;
import com.tt.collateralservice.service.AccoutService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    AccoutService accoutService;

    public static final String COLLATERAL_SERVICE="collateralService";
    @PostMapping("/v1.0/collateral")
    @CircuitBreaker(name=COLLATERAL_SERVICE, fallbackMethod = "getAvailableAccounts")
    public List<AccountCollateralResponse> calculateCollateral( @Validated @RequestBody final AccountCollateralRequest request) {

        //Get unique list of accounts from request body
        List<String> accounts = request.getAccounts().stream().distinct().toList();

        //Calculate account collateral value
        return accoutService.getAccountCollateral(accounts);
    }

    public List<AccountCollateralResponse> getAvailableAccounts(final AccountCollateralRequest request, final Exception e){

        return Stream.of( new AccountCollateralResponse("E1", BigDecimal.valueOf(5180.00))).collect(Collectors.toList());

    }
}
