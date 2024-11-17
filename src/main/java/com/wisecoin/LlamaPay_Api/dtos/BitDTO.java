package com.wisecoin.LlamaPay_Api.dtos;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BitDTO {
    private Long Id;

    @NotNull(message = "El titulo no puede ser nulo")
    @NotBlank(message = "El titulo no puede estar en blanco")
    private String title;

    @NotNull(message = "El contenido no puede ser nulo")
    @NotBlank(message = "El contenido no puede estar en blanco")
    private String content;

    @NotNull(message = "La imagen no puede ser nulo")
    @NotBlank(message = "La imagen no puede estar en blanco")
    private String image;
}
