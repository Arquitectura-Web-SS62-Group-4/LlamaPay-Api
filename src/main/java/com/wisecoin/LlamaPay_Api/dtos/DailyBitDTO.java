package com.wisecoin.LlamaPay_Api.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyBitDTO {
    private Long id;

    @NotNull(message = "La fecha no puede ser nulo")
    private LocalDate date;
}
