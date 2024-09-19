package com.wisecoin.LlamaPay_Api.serviceImpl;

import com.wisecoin.LlamaPay_Api.dtos.response.CategoryResponseDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.TypeBitResponseDTO;
import com.wisecoin.LlamaPay_Api.entities.Category;
import com.wisecoin.LlamaPay_Api.entities.TypeBit;
import com.wisecoin.LlamaPay_Api.exceptions.ResourceNotFoundException;
import com.wisecoin.LlamaPay_Api.repositories.TypeBitRepository;
import com.wisecoin.LlamaPay_Api.services.TypeBitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TypeBitImpl implements TypeBitService {

    @Autowired
    TypeBitRepository typeBitRepository;

    @Override
    public List<TypeBitResponseDTO> listAll() {
        List<TypeBitResponseDTO> tipos = new ArrayList<>();
        for(TypeBit typeBit: typeBitRepository.findAll()){
            TypeBitResponseDTO typeBitResponseDTO = new TypeBitResponseDTO(
                    typeBit.getId(), typeBit.getName()
            );
            tipos.add(typeBitResponseDTO);
        }
        return tipos;
    }

    @Override
    public TypeBit getTypeBitById(Long id) {
        TypeBit typeBit = typeBitRepository.findById(id).orElse(null);
        if (typeBit==null) {
            throw new ResourceNotFoundException("Tipo no encontrado");
        }
        return typeBit;
    }
}
