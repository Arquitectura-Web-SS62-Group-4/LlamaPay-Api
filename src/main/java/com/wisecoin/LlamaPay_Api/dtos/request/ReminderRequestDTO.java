package com.wisecoin.LlamaPay_Api.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReminderRequestDTO {

    private Long id;

    @NotNull(message = "El titulo no puede ser nulo")
    private String title;

    @NotNull(message = "Los detalles no pueden ser nulo")
    private String details;

    @NotNull(message = "La cantidad no puede ser nula")
    private Double amount;

    @NotNull(message = "La fecha de expiración no puede ser nula")
    private LocalDate expiration_date;

}
