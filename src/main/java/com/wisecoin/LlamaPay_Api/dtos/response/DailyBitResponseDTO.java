package com.wisecoin.LlamaPay_Api.dtos.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyBitResponseDTO {
    private Long Id;
    private Boolean view;
    private LocalDate date;
    private String title;
    private String content;
    private String image;
}
