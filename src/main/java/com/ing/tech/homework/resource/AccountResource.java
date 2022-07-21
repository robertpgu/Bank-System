package com.ing.tech.homework.resource;

import com.ing.tech.homework.dto.AccountDto;
import com.ing.tech.homework.dto.TransactionDto;
import com.ing.tech.homework.dto.wrappers.AccountDtoWrapper;
import com.ing.tech.homework.dto.wrappers.TransactionDtoWrapper;
import com.ing.tech.homework.service.AccountService;
import com.ing.tech.homework.utils.TransactionType;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/accounts")
public class AccountResource {
    private final AccountService accountService;

    @GetMapping
    ResponseEntity<List<AccountDtoWrapper>> getUserAccounts(Principal principal) {
        return ResponseEntity.ok(accountService.getAccountsByUser(principal.getName()));
    }

    @GetMapping("/{id}")
    ResponseEntity<AccountDtoWrapper> getById(@PathVariable("id") long id, Principal principal) {
        return ResponseEntity.ok(accountService.getById(id, principal));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('add_new_account')")
    ResponseEntity<AccountDto> save(@Valid @RequestBody AccountDto account, Principal principal) {
        return ResponseEntity.ok(accountService.save(account, principal));
    }

    @GetMapping("/{id}/income-history")
    @PreAuthorize("hasAuthority('see_transactions_history')")
    ResponseEntity<List<TransactionDto>> getAccountIncome(@PathVariable("id") long id, Principal principal) {
        return ResponseEntity.ok(accountService.getTransactions(id, TransactionType.INCOME, principal));
    }

    @GetMapping("/{id}/outcome-history")
    @PreAuthorize("hasAuthority('see_transactions_history')")
    ResponseEntity<List<TransactionDto>> getAccountOutcome(@PathVariable("id") long id, Principal principal) {
        return ResponseEntity.ok(accountService.getTransactions(id, TransactionType.OUTCOME, principal));
    }

    @GetMapping("/{id}/pending-list")
    @PreAuthorize("hasAuthority('see_pending_transactions')")
    ResponseEntity<List<TransactionDtoWrapper>> getPendingTransactions(@PathVariable("id") long id, Principal principal) {
        return ResponseEntity.ok(accountService.getAllPendingForUser(id, principal));
    }
}
