package ru.mirea.senebank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mirea.senebank.dto.account.AccountRequest;
import ru.mirea.senebank.dto.account.AccountResponse;
import ru.mirea.senebank.dto.info.OperationResultDto;
import ru.mirea.senebank.service.AccountService;

@RequiredArgsConstructor

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final AccountService accountService;

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @PostMapping
    public OperationResultDto open(@RequestBody AccountRequest request) {
        return accountService.createAccount(request);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @DeleteMapping("/{accountId}")
    public OperationResultDto close(@PathVariable Integer accountId) {
        return accountService.closeAccount(accountId);
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/{accountId}")
    public AccountResponse getAccountInfo(@PathVariable Integer accountId) {
        return accountService.getInfoAccountById(accountId);
    }
}
