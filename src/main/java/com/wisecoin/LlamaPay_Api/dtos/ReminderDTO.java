package com.wisecoin.LlamaPay_Api.dtos;

import com.wisecoin.LlamaPay_Api.entities.Client;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReminderDTO {

    private Long id;

    @NotNull(message = "El titulo no puede ser nulo")
    @NotBlank(message = "El titulo no puede estar vacio")
    private String title;

    @NotNull(message = "Los detalles no pueden ser nulo")
    @NotBlank(message = "Los detalles no pueden estar vacio")
    private String details;

    @NotNull(message = "La cantidad no puede ser nula")
    @NotBlank(message = "La cantidad no puede estar vacia")
    private Double amount;

    @NotNull(message = "La fecha de expiración no puede ser nula")
    @NotBlank(message = "La fecha de expiración no puede estar vacia")
    private LocalDateTime expiration_date;

}
