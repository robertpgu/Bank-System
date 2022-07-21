package com.ing.tech.homework.dto;

import com.ing.tech.homework.utils.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Data
@NoArgsConstructor @AllArgsConstructor
public class TransactionDto {

    @NotNull
    private Double amount;

    @NotNull
    private Long idFrom;

    @NotNull
    private Long idTo;

    private TransactionStatus transactionStatus;

    private Date transactionDate;

    public TransactionDto(Double amount, Long idFrom, Long idTo) {
        this.amount = amount;
        this.idFrom = idFrom;
        this.idTo = idTo;
    }

    public TransactionDto(Double amount, Long idFrom, Long idTo, TransactionStatus transactionStatus) {
        this.amount = amount;
        this.idFrom = idFrom;
        this.idTo = idTo;
        this.transactionStatus = transactionStatus;
        this.transactionDate = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
    }
}
