package ru.mirea.senebank.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mirea.senebank.dto.user.UserDto;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private UserDto user;
    private Boolean isOverdraft;
    private Integer balance;
}
