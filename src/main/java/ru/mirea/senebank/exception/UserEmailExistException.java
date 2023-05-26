package ru.mirea.senebank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class UserEmailExistException extends RuntimeException {
    public UserEmailExistException(String message) {
        super(message);
    }
}
