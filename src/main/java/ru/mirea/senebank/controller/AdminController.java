package ru.mirea.senebank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.mirea.senebank.dto.info.OperationResultDto;
import ru.mirea.senebank.dto.transaction.TransactionResponse;
import ru.mirea.senebank.service.TransactionService;
import ru.mirea.senebank.service.UserService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@PreAuthorize("hasRole('ADMIN')")
@RequestMapping("/admin")
public class AdminController {
    private final TransactionService transactionService;
    private final UserService userService;

    @GetMapping("/transactions")
    public List<TransactionResponse> getAllTransactions(
            @RequestParam(name = "userId", required = false) Integer userId,
            @RequestParam(name = "accountId", required = false) Integer accountId) {
        if (userId != null) {
            return transactionService.getAllTransactionsByUserId(userId);
        }
        if (accountId != null) {
            return transactionService.getAllTransactionsByAccountId(accountId);
        }

        return transactionService.getAllTransactions();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/users/{userId}")
    public OperationResultDto deleteUserByUserId(@PathVariable Integer userId) {
        return userService.deleteUserById(userId);
    }
}
