package com.wisecoin.LlamaPay_Api.dtos.request;

import jakarta.validation.constraints.Email;
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
public class ClientRequestDTO {
    private Long id;

    @NotNull(message = "El nombre no puede ser nulo")
    private String firstName;

    @NotNull(message = "El apellido no puede ser nulo")
    private String lastName;

    @NotNull(message = "El email no puede ser nulo")
    @Email(message = "El email no es valido")
    private String email;

    @NotNull(message = "El numero de telefono no puede ser nulo")
    private String phoneNumber;

    private LocalDate birthdate;

    @Size(max = 1, message = "El genero debe tener un solo carácter")
    private String gender;

    private String profilePicture;
}
