package com.ing.tech.homework.dto.wrappers;

import com.ing.tech.homework.entity.Account;
import com.ing.tech.homework.utils.CurrencyType;
import lombok.Data;

@Data
public class AccountDtoWrapper {
    private Long id;
    private Double balance;
    private CurrencyType currencyType;

    public AccountDtoWrapper(Account account) {
        this.id = account.getId();
        this.balance = account.getBalance();
        this.currencyType = account.getCurrencyType();
    }
}
