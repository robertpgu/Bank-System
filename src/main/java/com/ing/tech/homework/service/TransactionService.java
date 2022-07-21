package com.ing.tech.homework.service;

import com.ing.tech.homework.dto.TransactionDto;
import com.ing.tech.homework.entity.Account;
import com.ing.tech.homework.entity.Transaction;
import com.ing.tech.homework.exceptions.*;
import com.ing.tech.homework.repository.AccountRepository;
import com.ing.tech.homework.repository.TransactionRepository;
import com.ing.tech.homework.utils.TransactionStatus;
import com.ing.tech.homework.utils.TransactionType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final CurrencyExchangeService currencyExchangeService;

    public TransactionDto getById(long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(TransactionNotFoundException::new);

        return new TransactionDto(
                transaction.getAmount(),
                transaction.getFrom().getId(),
                transaction.getTo().getId(),
                transaction.getTransactionStatus(),
                transaction.getTransactionDate()
        );
    }

    public TransactionDto save(TransactionDto transactionDto) {
        Account accountFrom = accountRepository.findById(transactionDto.getIdFrom()).orElseThrow(UserNotFoundException::new);
        Account accountTo = accountRepository.findById(transactionDto.getIdTo()).orElseThrow(UserNotFoundException::new);
        Double amount = transactionDto.getAmount();
        TransactionStatus status = transactionDto.getTransactionStatus();

        if (transactionDto.getTransactionStatus().equals(TransactionStatus.COMPLETED)) {
            if (!moveMoney(accountTo, accountFrom, amount)) {
                status = TransactionStatus.REFUSED;
            }
        }

        Transaction transaction = transactionRepository.save(new Transaction(
                amount,
                accountFrom,
                accountTo,
                status,
                transactionDto.getTransactionDate()
        ));

        return new TransactionDto(
                transaction.getAmount(),
                transaction.getFrom().getId(),
                transaction.getTo().getId(),
                transaction.getTransactionStatus(),
                transaction.getTransactionDate()
        );
    }

    public TransactionDto transfer(TransactionDto transactionDto) {
        Account accountFrom = accountRepository.findById(transactionDto.getIdFrom()).orElseThrow(AccountNotFoundException::new);
        Account accountTo = accountRepository.findById(transactionDto.getIdTo()).orElseThrow(AccountNotFoundException::new);

        return save(new TransactionDto(

                transactionDto.getAmount(),
                accountFrom.getId(),
                accountTo.getId(),
                TransactionStatus.COMPLETED));
    }

    private boolean moveMoney(Account accountTo, Account accountFrom, Double amount) {

        if (accountFrom.getBalance() - amount >= 0.0) {
            accountService.updateBalance(accountFrom, amount, TransactionType.OUTCOME);
        } else {
            return false;
        }

        if (accountFrom.getCurrencyType().equals(accountTo.getCurrencyType())) {
            accountService.updateBalance(accountTo, amount, TransactionType.INCOME);
        } else {
            Double exchangeRate = currencyExchangeService.getExchangeRate(
                    accountFrom.getCurrencyType(),
                    accountTo.getCurrencyType()
            );

            accountService.updateBalance(accountTo, amount * exchangeRate, TransactionType.INCOME);
        }

        accountRepository.flush();

        return true;
    }

    public TransactionDto request(TransactionDto transactionDto) {
        Account accountFrom = accountRepository.findById(transactionDto.getIdFrom()).orElseThrow(AccountNotFoundException::new);
        Account accountTo = accountRepository.findById(transactionDto.getIdTo()).orElseThrow(AccountNotFoundException::new);

        return save(new TransactionDto(
                transactionDto.getAmount(),
                accountFrom.getId(),
                accountTo.getId(),
                TransactionStatus.PENDING));
    }

    public TransactionDto accept(long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(TransactionNotFoundException::new);

        if (!transaction.getTransactionStatus().equals(TransactionStatus.PENDING)) {
            throw new TransactionAlreadyFinalizedException();
        }

        transaction.setTransactionStatus(TransactionStatus.COMPLETED);

        if (!moveMoney(transaction.getTo(), transaction.getFrom(), transaction.getAmount())) {
            transaction.setTransactionStatus(TransactionStatus.REFUSED);
        }

        transactionRepository.flush();

        return new TransactionDto(
                transaction.getAmount(),
                transaction.getFrom().getId(),
                transaction.getTo().getId(),
                transaction.getTransactionStatus()
        );
    }

    public TransactionDto refuse(long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(TransactionNotFoundException::new);

        if (!transaction.getTransactionStatus().equals(TransactionStatus.PENDING)) {
            throw new TransactionAlreadyFinalizedException();
        }

        transaction.setTransactionStatus(TransactionStatus.REFUSED);

        transactionRepository.flush();

        return new TransactionDto(
                transaction.getAmount(),
                transaction.getFrom().getId(),
                transaction.getTo().getId(),
                transaction.getTransactionStatus()
        );
    }
}
