package com.wisecoin.LlamaPay_Api.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyFlowTypeDTO {
    private LocalDate date;
    private String type;
    private Double total;
}
