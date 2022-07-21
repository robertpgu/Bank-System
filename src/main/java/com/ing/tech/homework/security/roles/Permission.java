package com.ing.tech.homework.security.roles;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Permission {

    TRANSFER_MONEY("transfer_money"),
    REQUEST_MONEY("request_money"),
    READ_BALANCES("read_balances"),
    ACCEPT_MONEY("accept_money"),
    REFUSE_MONEY("refuse_money"),
    SEE_PENDING_TRANSACTIONS("see_pending_transactions"),
    SEE_TRANSACTIONS_HISTORY("see_transactions_history"),
    ADD_NEW_ACCOUNT("add_new_account");

    private final String permission;
}
