package ru.mirea.senebank.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.mirea.senebank.config.JwtConfig;
import ru.mirea.senebank.dto.info.ErrorResponse;
import ru.mirea.senebank.dto.security.UserSecurityInfo;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
public class JwtAccessTokenVerifier extends OncePerRequestFilter {
    private final JwtService jwtService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) {

        String authorizationHeader = request.getHeader(JwtConfig.HEADER);

        if (authorizationHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String accessToken = authorizationHeader.replace(JwtConfig.TOKEN_PREFIX, "");
        String message;
        try {
            UserSecurityInfo userSecurityInfo = jwtService.validateToken(accessToken);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    userSecurityInfo.getUsername(), null, userSecurityInfo.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);

            filterChain.doFilter(request, response);
            return;

        } catch (MalformedJwtException ex) {
            message = "Malformed JWT";
        } catch (ExpiredJwtException ex) {
            message = "Invalid signature";
        } catch (SignatureException ex) {
            message = "JWT expired";
        } catch (Throwable ex) {
            message = "Unexpected error";
        }

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        new ObjectMapper().writeValue(response.getOutputStream(), new ErrorResponse(message));
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }
}
