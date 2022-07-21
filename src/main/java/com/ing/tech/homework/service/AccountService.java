package com.ing.tech.homework.service;


import com.ing.tech.homework.dto.AccountDto;
import com.ing.tech.homework.dto.TransactionDto;
import com.ing.tech.homework.dto.wrappers.AccountDtoWrapper;
import com.ing.tech.homework.dto.wrappers.TransactionDtoWrapper;
import com.ing.tech.homework.entity.Account;
import com.ing.tech.homework.entity.Transaction;
import com.ing.tech.homework.entity.User;
import com.ing.tech.homework.exceptions.AccountNotFoundException;
import com.ing.tech.homework.exceptions.InvalidAccountAccessException;
import com.ing.tech.homework.exceptions.UserNotFoundException;
import com.ing.tech.homework.repository.AccountRepository;
import com.ing.tech.homework.repository.TransactionRepository;
import com.ing.tech.homework.repository.UserRepository;
import com.ing.tech.homework.utils.TransactionStatus;
import com.ing.tech.homework.utils.TransactionType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;

    public AccountDto save(AccountDto accountDto, Principal principal) {
        User user = userRepository.findUserByUsername(principal.getName()).orElseThrow(UserNotFoundException::new);
        Account account = accountRepository.save(new Account(
                accountDto.getBalance(),
                accountDto.getCurrencyType(),
                user
        ));
        return new AccountDto(
                account.getBalance(),
                account.getCurrencyType(),
                account.getUser().getId()
        );
    }

    public AccountDtoWrapper getById(Long id, Principal principal) {
        Account account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        if (!account.getUser().getUsername().equals(principal.getName())) {
            throw new InvalidAccountAccessException();
        }

        return new AccountDtoWrapper(account);
    }

    public List<AccountDtoWrapper> getAccountsByUser(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(UserNotFoundException::new);
        return accountRepository.findAll().stream()
                .filter(account -> account.getUser().getId().equals(user.getId()))
                .map(AccountDtoWrapper::new).collect(Collectors.toList());
    }

    public List<TransactionDto> getTransactions(long accountId, TransactionType transactionType, Principal principal) {
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);

        if (!account.getUser().getUsername().equals(principal.getName())) {
            throw new InvalidAccountAccessException();
        }

        Set<Transaction> transactions;

        if (transactionType.equals(TransactionType.OUTCOME)) {
            transactions = account.getTransactionsFrom();
        } else {
            transactions = account.getTransactionsTo();
        }

        return transactions.stream().map(transaction -> new TransactionDto(
                transaction.getAmount(),
                transaction.getFrom().getId(),
                transaction.getTo().getId(),
                transaction.getTransactionStatus(),
                transaction.getTransactionDate()
        )).collect(Collectors.toList());
    }

    public void updateBalance(Account account, Double amount, TransactionType transactionType) {
        double updatedBalance;

        if (transactionType.equals(TransactionType.INCOME)) {
            updatedBalance = account.getBalance() + amount;
        } else {
            updatedBalance = account.getBalance() - amount;
        }

        account.setBalance(updatedBalance);
    }

    public List<TransactionDtoWrapper> getAllPendingForUser(long accountId, Principal principal) {
        Account account = accountRepository.findById(accountId).orElseThrow(AccountNotFoundException::new);

        if (!account.getUser().getUsername().equals(principal.getName())) {
            throw new InvalidAccountAccessException();
        }

        return transactionRepository.findAll().stream()
                .filter(transaction -> ((transaction.getTo().getId().equals(accountId)
                        || transaction.getFrom().getId().equals(accountId)))
                        && transaction.getTransactionStatus().equals(TransactionStatus.PENDING))
                .map(TransactionDtoWrapper::new)
                .collect(Collectors.toList());
    }
}
