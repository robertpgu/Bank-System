package com.ing.tech.homework.dto.wrappers;

import com.ing.tech.homework.entity.Transaction;
import com.ing.tech.homework.utils.TransactionStatus;
import lombok.Data;

import java.util.Date;

@Data
public class TransactionDtoWrapper {
    private final Long id;
    private final Double amount;
    private final Long idFrom;
    private final Long idTo;
    private final TransactionStatus transactionStatus;
    private final Date transactionDate;

    public TransactionDtoWrapper(Transaction transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.idFrom = transaction.getFrom().getId();
        this.idTo = transaction.getTo().getId();
        this.transactionStatus = transaction.getTransactionStatus();
        this.transactionDate = transaction.getTransactionDate();
    }
}
