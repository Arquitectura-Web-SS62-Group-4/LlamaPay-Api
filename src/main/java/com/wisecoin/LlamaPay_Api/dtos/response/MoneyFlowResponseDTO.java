package com.wisecoin.LlamaPay_Api.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyFlowResponseDTO {
    private Long id;
    private String name;
    private String type;
    private Double amount;
    private LocalDate date;
    private String category;
}
