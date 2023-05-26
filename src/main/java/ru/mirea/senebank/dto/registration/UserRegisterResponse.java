package ru.mirea.senebank.dto.registration;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisterResponse {
    private String status;
    private String message;
}
