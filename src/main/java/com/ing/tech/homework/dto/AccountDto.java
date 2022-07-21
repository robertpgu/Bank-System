package com.ing.tech.homework.dto;

import com.ing.tech.homework.utils.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor @AllArgsConstructor
public class AccountDto {

    @NotNull
    private Double balance;
    @NotNull
    private CurrencyType currencyType;
    private Long userId;

    public AccountDto(Double balance, CurrencyType currencyType) {
        this.balance = balance;
        this.currencyType = currencyType;
    }
}
