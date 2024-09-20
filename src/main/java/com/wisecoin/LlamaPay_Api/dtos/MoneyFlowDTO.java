package com.wisecoin.LlamaPay_Api.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wisecoin.LlamaPay_Api.entities.Category;
import com.wisecoin.LlamaPay_Api.entities.Client;
import jakarta.persistence.*;
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
public class MoneyFlowDTO {

    private Long id;

    @NotNull(message = "El tipo no puede ser nulo")
    @NotBlank(message = "El tipo no puede estar en blanco")
    @Size(max = 100, message = "El tamaño maximo de la descripcion es 100 caracteres")
    private String name;

    @NotNull(message = "El tipo no puede ser nulo")
    @NotBlank(message = "El tipo no puede estar en blanco")
    private String type; // "Ingreso" o "Gasto"

    @NotNull(message = "El monto no puede ser nulo")
    private Double amount;

    @NotNull(message = "La fecha no puede ser nulo")
    private LocalDate date;
}
