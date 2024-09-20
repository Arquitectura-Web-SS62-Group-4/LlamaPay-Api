package com.wisecoin.LlamaPay_Api.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wisecoin.LlamaPay_Api.entities.Client;
import com.wisecoin.LlamaPay_Api.entities.TypeBit;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyBitDTO {

    private Long Id;

    @NotNull(message = "El titulo no puede ser nulo")
    @NotBlank(message = "El titulo no puede estar en blanco")
    private String title;

    @NotNull(message = "El contenido no puede ser nulo")
    @NotBlank(message = "El contenido no puede estar en blanco")
    private String content;
}
