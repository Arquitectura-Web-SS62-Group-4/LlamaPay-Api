package com.wisecoin.LlamaPay_Api.services;

import com.wisecoin.LlamaPay_Api.dtos.DailyBitDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.DailyBitResponseDTO;
import com.wisecoin.LlamaPay_Api.entities.DailyBit;

import java.time.LocalDate;

public interface DailyBitService {
    public DailyBit addDailyBit(Long client_id, DailyBitDTO dailyBitDTO);
    public DailyBitResponseDTO getDailyBitResponseById(Long client_id, LocalDate date);
    public DailyBit getDailyBitById(Long id);
    public DailyBit updateDailyBit(Long id);
}
