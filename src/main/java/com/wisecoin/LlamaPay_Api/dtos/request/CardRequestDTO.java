package com.wisecoin.LlamaPay_Api.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardRequestDTO {
    private Long id;

    @NotNull(message = "El numero de tarjeta no puede ser nulo")
    private String cardNumber;

    @NotNull(message = "La descripcion no puede ser nulo")
    private String ownerName;

    @NotNull(message = "La fecha de expiración no puede ser nulo")
    private String expirationDate;

    @NotNull(message = "El cvv no puede ser nulo")
    private String cvv;
}
