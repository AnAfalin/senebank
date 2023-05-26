package ru.mirea.senebank.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mirea.senebank.entity.Account;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionRequest {
    private Integer userId;

    @NotBlank(message = "Id accountFrom cannot be empty or null")
    private Integer accountFromId;

    @NotBlank(message = "Id accountTo cannot be empty or null")
    private Integer accountToId;

    @NotBlank(message = "Payload cannot be empty or null")
    private Integer payload;
}
