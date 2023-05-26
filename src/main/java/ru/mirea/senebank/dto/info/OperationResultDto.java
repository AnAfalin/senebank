package ru.mirea.senebank.dto.info;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OperationResultDto {
    private String status;
    private Boolean success;
    private String message;
}
