package com.wisecoin.LlamaPay_Api.dtos;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wisecoin.LlamaPay_Api.entities.Client;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class GoalDTO {

    private Long id;

    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "El nombre no puede estar en blanco")
    private String name;

    @NotNull(message = "La descripcion no puede ser nulo")
    @NotBlank(message = "La descripcion no puede estar en blanco")
    @Size(max = 250, message = "El tamaño maximo de la descripcion es 250 caracteres")
    private String description;

    @NotNull(message = "El precio no puede ser nulo")
    private Double amount;

    @NotNull(message = "La fecha de inicio no puede ser nulo")
    //@NotBlank(message = "La fecha de inicio no puede estar en blanco")
    private LocalDate startDate;

    //@NotBlank(message = "La fecha limite no puede estar en blanco")
    @NotNull(message = "La fecha limite no puede ser nulo")
    private LocalDate deadline;
}
