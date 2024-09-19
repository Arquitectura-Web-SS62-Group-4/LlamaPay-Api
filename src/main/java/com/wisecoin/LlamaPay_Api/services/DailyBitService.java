package com.wisecoin.LlamaPay_Api.services;

import com.wisecoin.LlamaPay_Api.dtos.DailyBitDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.DailyBitRequestDTO;
import com.wisecoin.LlamaPay_Api.entities.DailyBit;

import java.util.List;

public interface DailyBitService {
    public DailyBit addDailyBit(Long client_id, Long typebit_id, DailyBitDTO dailyBitDTO);
    public List<DailyBit> findByClient(Long client_id);
    public List<DailyBit> listAll();
    public void deleteDailyBit(Long id);
    public DailyBit getDailyBitById(Long id);
    public DailyBit updateDailyBit(Long id, Long typebit_id, DailyBitRequestDTO dailyBitRequestDTO);
}
