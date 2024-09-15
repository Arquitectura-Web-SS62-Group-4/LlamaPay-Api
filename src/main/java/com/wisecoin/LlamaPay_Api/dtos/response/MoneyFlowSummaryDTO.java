package com.wisecoin.LlamaPay_Api.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyFlowSummaryDTO {
   private String monthName;
   private Double netAmount;
}
