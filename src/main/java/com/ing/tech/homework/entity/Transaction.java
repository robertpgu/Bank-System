package com.ing.tech.homework.entity;

import com.ing.tech.homework.utils.TransactionStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor @AllArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column @Min(value = 0)
    private Double amount;

    @ManyToOne(cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Account from;

    @ManyToOne(cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Account to;

    @Enumerated(EnumType.ORDINAL)
    private TransactionStatus transactionStatus;
    private Date transactionDate;

    public Transaction(Double amount, Account from, Account to, TransactionStatus transactionStatus, Date transactionDate) {
        this.amount = amount;
        this.from = from;
        this.to = to;
        this.transactionStatus = transactionStatus;
        this.transactionDate = transactionDate;
    }
}

