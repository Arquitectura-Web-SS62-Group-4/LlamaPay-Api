package com.wisecoin.LlamaPay_Api.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyFlowResponseDTO {
    private String nameCategory;
    private Double total;
}
