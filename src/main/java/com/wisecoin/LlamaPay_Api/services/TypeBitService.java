package com.wisecoin.LlamaPay_Api.services;

import com.wisecoin.LlamaPay_Api.dtos.response.TypeBitResponseDTO;
import com.wisecoin.LlamaPay_Api.entities.TypeBit;

import java.util.List;

public interface TypeBitService {
    public List<TypeBitResponseDTO> listAll();
    public TypeBit getTypeBitById(Long id);
}
