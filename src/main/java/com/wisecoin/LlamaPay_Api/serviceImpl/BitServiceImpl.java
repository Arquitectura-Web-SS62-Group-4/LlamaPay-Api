package com.wisecoin.LlamaPay_Api.serviceImpl;


import com.wisecoin.LlamaPay_Api.dtos.BitDTO;
import com.wisecoin.LlamaPay_Api.entities.Bit;
import com.wisecoin.LlamaPay_Api.exceptions.ResourceNotFoundException;
import com.wisecoin.LlamaPay_Api.exceptions.ValidationException;
import com.wisecoin.LlamaPay_Api.repositories.BitRepository;
import com.wisecoin.LlamaPay_Api.services.BitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BitServiceImpl implements BitService {
    @Autowired
    BitRepository bitRepository;

    @Override
    public Bit addBit(BitDTO bitDTO) {
        if(bitDTO.getTitle().length()<=2){
            throw new ValidationException("El titulo no puede tener menos de tres caracteres");
        }

        if(bitDTO.getContent().length()<=2){
            throw new ValidationException("El contenido no puede tener menos de tres caracteres");
        }

        Bit bit = new Bit(0L, bitDTO.getTitle(), bitDTO.getContent(), bitDTO.getImage());
        bit = bitRepository.save(bit);
        return bit;
    }

    @Override
    public Bit getBitById(Long id) {
        Bit bit = bitRepository.findById(id).orElse(null);
        if (bit==null) {
            return null;
        }
        return bit;
    }
}
