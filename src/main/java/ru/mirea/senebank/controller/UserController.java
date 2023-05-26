package ru.mirea.senebank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.mirea.senebank.dto.login.UserLoginRequest;
import ru.mirea.senebank.dto.login.UserLoginResponse;
import ru.mirea.senebank.dto.registration.UserRegisterRequest;
import ru.mirea.senebank.dto.registration.UserRegisterResponse;
import ru.mirea.senebank.service.UserService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/reg")
    public UserRegisterResponse registrationUser(@Valid @RequestBody UserRegisterRequest request) {
        return userService.registerUser(request);
    }

    @PostMapping("/auth/login")
    public UserLoginResponse login(@Valid @RequestBody UserLoginRequest request) {
        return userService.login(request);
    }
}
