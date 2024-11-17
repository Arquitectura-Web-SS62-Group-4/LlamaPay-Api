package com.wisecoin.LlamaPay_Api.services;

import com.wisecoin.LlamaPay_Api.dtos.BitDTO;
import com.wisecoin.LlamaPay_Api.entities.Bit;

public interface BitService {
    public Bit addBit (BitDTO bitDTO);
    public Bit getBitById(Long id);
}
