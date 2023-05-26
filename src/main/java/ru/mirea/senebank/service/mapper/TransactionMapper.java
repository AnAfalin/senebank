package ru.mirea.senebank.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.mirea.senebank.dto.transaction.TransactionRequest;
import ru.mirea.senebank.dto.transaction.TransactionResponse;
import ru.mirea.senebank.entity.Transaction;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    Transaction toTransaction(TransactionRequest transactionRequest);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "accountFromId", source = "accountFrom.id")
    @Mapping(target = "accountToId", source = "accountTo.id")
    TransactionResponse toTransactionResponse(Transaction transaction);

    List<TransactionResponse> toTransactionResponse(List<Transaction> transactions);
}
