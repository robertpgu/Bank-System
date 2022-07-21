package com.ing.tech.homework.entity;

import com.ing.tech.homework.utils.CurrencyType;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) @Min(value = 0)
    private Double balance;

    @Column(nullable = false)
    private CurrencyType currencyType;

    @ManyToOne(cascade = CascadeType.ALL)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "to", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Transaction> transactionsTo;

    @OneToMany(mappedBy = "from", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Transaction> transactionsFrom;

    public Account(Double balance, CurrencyType currencyType, User user) {
        this.balance = balance;
        this.currencyType = currencyType;
        this.user = user;
    }

    public Account(CurrencyType currencyType, User user, Set<Transaction> transactionsTo, Set<Transaction> transactionsFrom) {
        this.balance = 0.0;
        this.currencyType = currencyType;
        this.user = user;
        this.transactionsTo = transactionsTo;
        this.transactionsFrom = transactionsFrom;
    }
}
