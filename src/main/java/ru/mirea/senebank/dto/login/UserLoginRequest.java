package ru.mirea.senebank.dto.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginRequest {
    @NotBlank(message = "Email cannot be empty or null")
    private String email;
    @NotBlank(message = "Password cannot be empty or null")
    private String password;
}