package com.wisecoin.LlamaPay_Api.dtos.request;

import com.wisecoin.LlamaPay_Api.entities.TypeBit;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyBitRequestDTO {
    private Long Id;

    @NotNull(message = "El titulo no puede ser nulo")
    private String title;

    @NotNull(message = "El contenido no puede ser nulo")
    private String content;
}
