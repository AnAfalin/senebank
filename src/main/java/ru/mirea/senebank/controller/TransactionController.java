package ru.mirea.senebank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.senebank.dto.info.OperationResultDto;
import ru.mirea.senebank.dto.transaction.TransactionRequest;
import ru.mirea.senebank.service.TransactionService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public OperationResultDto transferMoney(@Valid @RequestBody TransactionRequest transactionRequest) {
        return transactionService.transferMoney(transactionRequest);
    }
}
