package com.ing.tech.homework.resource;

import com.ing.tech.homework.dto.TransactionDto;
import com.ing.tech.homework.dto.wrappers.TransactionIdWrapper;
import com.ing.tech.homework.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactionResource {
    private final TransactionService transactionService;

    @GetMapping("/{id}")
    ResponseEntity<TransactionDto> getById(@PathVariable("id") long id) {
        return ResponseEntity.ok(transactionService.getById(id));
    }

    @PostMapping("/transfer")
    @PreAuthorize("hasAuthority('transfer_money')")
    ResponseEntity<TransactionDto> transferMoney(@Valid @RequestBody TransactionDto transactionDto) {
        return ResponseEntity.ok(transactionService.transfer(transactionDto));
    }

    @PostMapping("/request")
    @PreAuthorize("hasAuthority('request_money')")
    ResponseEntity<TransactionDto> requestMoney(@Valid @RequestBody TransactionDto transactionDto) {
        return ResponseEntity.ok(transactionService.request(transactionDto));
    }

    @PostMapping("/accept")
    @PreAuthorize("hasAuthority('accept_money')")
    ResponseEntity<TransactionDto> acceptTransaction(@Valid @RequestBody TransactionIdWrapper transactionIdWrapper) {
        return ResponseEntity.ok(transactionService.accept(transactionIdWrapper.getId()));
    }

    @PostMapping("/refuse")
    @PreAuthorize("hasAuthority('refuse_money')")
    ResponseEntity<TransactionDto> refuseTransaction(@Valid @RequestBody TransactionIdWrapper transactionIdWrapper) {
        return ResponseEntity.ok(transactionService.refuse(transactionIdWrapper.getId()));
    }
}
