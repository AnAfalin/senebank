package ru.mirea.senebank.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import ru.mirea.senebank.dto.info.ErrorResponse;
import ru.mirea.senebank.dto.login.UserLoginRequest;
import ru.mirea.senebank.dto.security.TokenDto;
import ru.mirea.senebank.dto.security.UserTokenResponse;
import ru.mirea.senebank.model.TypeToken;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class AuthFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        if (!"POST".equals(request.getMethod())) {
            throw new AuthenticationServiceException("Request should be POST");
        }

        UserLoginRequest userLoginRequest = objectMapper.readValue(request.getInputStream(), UserLoginRequest.class);

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword());

        return authenticationManager.authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        TokenDto newAccessToken = jwtService.generateToken(authResult.getName(),
                authResult.getAuthorities(),
                TypeToken.ACCESS);
        TokenDto newRefreshToken = jwtService.generateToken(authResult.getName(),
                authResult.getAuthorities(),
                TypeToken.REFRESH);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(response.getOutputStream(),
                new UserTokenResponse(authResult.getName(), newAccessToken, newRefreshToken));

        SecurityContextHolder.getContext().setAuthentication(authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        ErrorResponse errorResponse = new ErrorResponse("Password or login is incorrect");

        objectMapper.writeValue(response.getOutputStream(), errorResponse);
    }
}
