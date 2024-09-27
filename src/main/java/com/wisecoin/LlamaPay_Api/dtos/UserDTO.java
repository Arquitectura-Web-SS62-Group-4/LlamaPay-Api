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
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;

    @NotBlank(message = "El username no puede estar en blanco")
    private String username;

    @NotBlank(message = "La contraseña no puede estar en blanco")
    private String password;

    private String authorities;
}
