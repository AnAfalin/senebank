package ru.mirea.senebank.dto.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserTokenResponse {
    private String email;
    private TokenDto accessToken;
    private TokenDto refreshToken;
}
