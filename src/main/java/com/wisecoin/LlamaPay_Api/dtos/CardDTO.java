package com.wisecoin.LlamaPay_Api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDTO {
    private Long id;

    @NotNull(message = "El numero de tarjeta no puede ser nulo")
    @NotBlank(message = "El numero de tarjeta no puede estar en blanco")
    private String cardNumber;

    @NotNull(message = "La descripcion no puede ser nulo")
    @NotBlank(message = "La descripcion no puede estar en blanco")
    private String ownerName;

    @NotNull(message = "La fecha de expiración no puede ser nulo")
    private String expirationDate;

    @NotNull(message = "El cvv no puede ser nulo")
    @NotBlank(message = "El cvv no puede estar en blanco")
    private String cvv;
}
