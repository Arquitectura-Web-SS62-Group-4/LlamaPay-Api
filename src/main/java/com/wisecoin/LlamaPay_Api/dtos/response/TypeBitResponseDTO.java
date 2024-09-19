package com.wisecoin.LlamaPay_Api.dtos.response;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TypeBitResponseDTO {
    private Long Id;

    private String name;
}
