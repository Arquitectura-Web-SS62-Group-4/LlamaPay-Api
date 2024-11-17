package com.wisecoin.LlamaPay_Api.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoalResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double amount;
    private LocalDate startDate;
    private LocalDate deadline;
    private Boolean isSuccessfull;
    private Double netAmount;
}
