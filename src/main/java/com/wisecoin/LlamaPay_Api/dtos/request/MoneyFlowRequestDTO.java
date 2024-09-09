package com.wisecoin.LlamaPay_Api.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyFlowRequestDTO {
    private Long id;

    @NotNull(message = "El tipo no puede ser nulo")
    private String type; // "Ingreso" o "Gasto"

    @NotNull(message = "El monto no puede ser nulo")
    private Double amount;

    @NotNull(message = "La fecha no puede ser nulo")
    private LocalDate date;

    @NotNull(message = "El tipo no puede ser nulo")
    @Size(max = 100, message = "El tamaño maximo de la descripcion es 100 caracteres")
    private String name;
}
