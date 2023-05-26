package ru.mirea.senebank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class NoEnoughMoneyBalance extends RuntimeException {
    public NoEnoughMoneyBalance(String message) {
        super(message);
    }
}
