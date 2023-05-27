package ru.mirea.senebank.dto.login;

import lombok.*;
import ru.mirea.senebank.dto.security.TokenDto;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse {
    private String email;
    private List<String> roles;
    private TokenDto accessToken;
    private TokenDto refreshToken;
}
