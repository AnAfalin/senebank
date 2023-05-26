package ru.mirea.senebank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.mirea.senebank.dto.info.OperationResultDto;
import ru.mirea.senebank.dto.transaction.TransactionRequest;
import ru.mirea.senebank.dto.transaction.TransactionResponse;
import ru.mirea.senebank.entity.Account;
import ru.mirea.senebank.entity.Transaction;
import ru.mirea.senebank.exception.NoEnoughMoneyBalance;
import ru.mirea.senebank.repository.TransactionRepository;
import ru.mirea.senebank.service.mapper.TransactionMapper;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final TransactionMapper transactionMapper;

    public OperationResultDto transferMoney(TransactionRequest transactionRequest) {
        Account accountTo = accountService.findAccountById(transactionRequest.getAccountToId());
        Account accountFrom = accountService.findAccountById(transactionRequest.getAccountFromId());

        Transaction transaction = transactionMapper.toTransaction(transactionRequest);

        if (accountFrom.getBalance() < transaction.getPayload()) {
            throw new NoEnoughMoneyBalance("Insufficient money on the balance sheet");
        }

        transaction.setAccountTo(accountTo);
        transaction.setAccountFrom(accountFrom);

        accountFrom.setBalance(accountFrom.getBalance() - transaction.getPayload());
        accountTo.setBalance(accountTo.getBalance() + transaction.getPayload());

        transactionRepository.save(transaction);

        return OperationResultDto.builder()
                .status(HttpStatus.OK.toString())
                .success(true)
                .message(("The transfer of %S from account with id='%s' to account with id='%s' " +
                        "has been successfully completed")
                        .formatted(transaction.getPayload(), accountFrom.getId(), accountTo.getId()))
                .build();
    }

    public List<TransactionResponse> getAllTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();
        return transactionMapper.toTransactionResponse(transactions);
    }

    public List<TransactionResponse> getAllTransactionsByUserId(Integer id) {
        List<Transaction> transactions = transactionRepository.findAllByUserId(id);
        return transactionMapper.toTransactionResponse(transactions);
    }

    public List<TransactionResponse> getAllTransactionsByAccountId(Integer id) {
        List<Transaction> transactions = transactionRepository.findAllByAccountId(id);
        return transactionMapper.toTransactionResponse(transactions);
    }
}
