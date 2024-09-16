package com.wisecoin.LlamaPay_Api.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SettingDTO {
    private Long id;

    @NotNull(message = "El tema no puede ser nulo")
    private String theme;

    @NotNull(message = "El tamaño no puede ser nulo")
    private String size;

    @NotNull(message = "El lenguaje no puede ser nulo")
    private String language;
}
