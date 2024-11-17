package com.wisecoin.LlamaPay_Api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserClientDTO {
    private UserDTO userDTO;
    private ClientDTO clientDTO;
}
