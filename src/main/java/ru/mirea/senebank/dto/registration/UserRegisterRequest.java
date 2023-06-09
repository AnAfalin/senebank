package ru.mirea.senebank.dto.registration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterRequest {

    @NotBlank(message = "Email cannot be empty or null")
    @Email(regexp = "[\\w._]{1,10}@[\\w]{2,}.[\\w]{2,}", message = "Email is not format as email (email@email.com)")
    private String email;

    @Pattern(regexp = "[A-Za-z0-9._]{5,15}",
            message = "Password must contains 5-15 characters (uppercase letters, lowercase letters or numbers )")
    private String password;
}