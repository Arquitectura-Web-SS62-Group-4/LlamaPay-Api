package com.wisecoin.LlamaPay_Api.dtos;

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
public class ClientDTO {

    private Long id;

    @NotNull(message = "El nombre no puede ser nulo")
    @NotBlank(message = "El nombre no puede estar en blanco")
    private String firstName;

    @NotNull(message = "El apellido no puede ser nulo")
    @NotBlank(message = "El apellido no puede estar en blanco")
    private String lastName;

    @NotNull(message = "El email no puede ser nulo")
    @NotBlank(message = "El email no puede estar en blanco")
    @Email(message = "El email no es valido")
    private String email;

    @NotBlank(message = "El numero de telefono no puede estar en blanco")
    @NotNull(message = "El numero de telefono no puede ser nulo")
    private String phoneNumber;

    @NotBlank(message = "La fecha de nacimiento no puede estar en blanco")
    //@NotNull(message = "La fecha de nacimiento no puede ser nulo")
    private LocalDate birthdate;

    @NotBlank(message = "El genero no puede estar en blanco")
    @NotNull(message = "El genero no puede ser nulo")
    @Size(max = 1, message = "El genero debe tener un solo carácter")
    private String gender;

}
