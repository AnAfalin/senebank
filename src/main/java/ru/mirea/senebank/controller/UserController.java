package ru.mirea.senebank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import ru.mirea.senebank.dto.login.UserLoginRequest;
import ru.mirea.senebank.dto.login.UserLoginResponse;
import ru.mirea.senebank.dto.registration.UserRegisterRequest;
import ru.mirea.senebank.dto.registration.UserRegisterResponse;
import ru.mirea.senebank.security.SecurityUser;
import ru.mirea.senebank.service.UserService;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/auth/reg")
    public UserRegisterResponse registrationUser(@Valid @RequestBody UserRegisterRequest request) {
        return userService.registerUser(request);
    }

//    @PostMapping("/auth/login")
//    public UserLoginResponse login(@Valid @RequestBody UserLoginRequest request) {
//        try {
//            Authentication authentication = authenticationManager
//                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
//            //success
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            SecurityUser user = (SecurityUser) authentication.getPrincipal();
//
//            //вызов фильтра токена и выдача токена
//
//            return new UserLoginResponse(user.getUsername(),
//                    authentication.getAuthorities().stream()
//                            .map(GrantedAuthority::getAuthority)
//                            .collect(Collectors.toList()),
//                    null, null);
//        } catch (AuthenticationException exception) {
//            //unsuccess
//            throw new UsernameNotFoundException(exception.getMessage());
//        }
//
//    }
}
