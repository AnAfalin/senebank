package ru.mirea.senebank.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.mirea.senebank.dto.account.AccountRequest;
import ru.mirea.senebank.dto.account.AccountResponse;
import ru.mirea.senebank.dto.info.OperationResultDto;
import ru.mirea.senebank.entity.Account;
import ru.mirea.senebank.entity.User;
import ru.mirea.senebank.exception.NoSuchAccountException;
import ru.mirea.senebank.exception.NoSuchUserException;
import ru.mirea.senebank.repository.AccountRepository;
import ru.mirea.senebank.service.mapper.AccountMapper;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final UserService userService;

    public OperationResultDto createAccount(AccountRequest request) {
        User foundUser = userService.findUserById(request.getUserId());
        Account account = accountMapper.toAccount(request);

        account.setUser(foundUser);

        accountRepository.save(account);

        return OperationResultDto.builder()
                .status(HttpStatus.CREATED.toString())
                .success(true)
                .message("Account successful created")
                .build();
    }

    public OperationResultDto closeAccount(Integer id) {
        Account foundAccount = accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchUserException("Account with id='%s' not found".formatted(id)));

        accountRepository.delete(foundAccount);

        return OperationResultDto.builder()
                .status(HttpStatus.OK.toString())
                .success(true)
                .message("User account with id='%s' successful deleted".formatted(id))
                .build();
    }

    public AccountResponse getInfoAccountById(Integer id) {
        Account account = accountRepository.findAccountWithUserById(id)
                .orElseThrow(() -> new NoSuchAccountException("Account with id='%s' not found".formatted(id)));

        return accountMapper.toAccountResponse(account);
    }

    public Account findAccountById(Integer id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchAccountException("Account with id='%s' not found".formatted(id)));
    }
}
