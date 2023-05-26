package ru.mirea.senebank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoSuchAccountException extends NoSuchElementException {
    public NoSuchAccountException(String message) {
        super(message);
    }
}
