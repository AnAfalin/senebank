package ru.mirea.senebank.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private Integer id;
    private Integer userId;
    private Integer accountFromId;
    private Integer accountToId;
    private Integer payload;
}
