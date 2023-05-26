package ru.mirea.senebank.service.mapper;

import org.mapstruct.Mapper;
import ru.mirea.senebank.dto.account.AccountRequest;
import ru.mirea.senebank.dto.account.AccountResponse;
import ru.mirea.senebank.entity.Account;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    Account toAccount(AccountRequest accountRequest);

    AccountResponse toAccountResponse(Account account);
}
