package com.wisecoin.LlamaPay_Api.dtos.request;

import com.wisecoin.LlamaPay_Api.entities.Client;
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
public class GoalRequestDTO {

    private Long id;

    @NotNull(message = "El nombre no puede ser nulo")
    private String name;

    @NotNull(message = "La descripcion no puede ser nulo")
    @Size(max = 250, message = "El tamaño maximo de la descripcion es 250 caracteres")
    private String description;

    private Double amount;

    private LocalDate startDate;

    private LocalDate deadline;
}
