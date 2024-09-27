package com.wisecoin.LlamaPay_Api.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TokenDTO {
    private String jwtToken;
    private Long user_id;
    private String authorities;
}
