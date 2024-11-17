package com.wisecoin.LlamaPay_Api.serviceImpl;

import com.wisecoin.LlamaPay_Api.dtos.DailyBitDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.DailyBitResponseDTO;
import com.wisecoin.LlamaPay_Api.entities.*;
import com.wisecoin.LlamaPay_Api.exceptions.ResourceNotFoundException;
import com.wisecoin.LlamaPay_Api.repositories.BitRepository;
import com.wisecoin.LlamaPay_Api.repositories.ClientRepository;
import com.wisecoin.LlamaPay_Api.repositories.DailyBitRepository;
import com.wisecoin.LlamaPay_Api.services.BitService;
import com.wisecoin.LlamaPay_Api.services.DailyBitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class DailyBitServiceImpl implements DailyBitService {

    @Autowired
    DailyBitRepository dailyBitRepository;

    @Autowired
    BitService bitService;

    @Autowired
    BitRepository bitRepository;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public DailyBit addDailyBit(Long client_id, DailyBitDTO dailyBitDTO) {
        Client client = clientRepository.findById(client_id).orElse(null);

        if (client==null) {
            throw new ResourceNotFoundException("Cliente no encontrado");
        }

        Long bit_id;
        Bit bit = null;
        boolean bitExists = true;

        while (bitExists) {
            bit_id = generateUniqueBitId();
            bit = bitService.getBitById(bit_id);

            if (bit != null && !dailyBitRepository.existsByClient_idAndBit_idAndView(client_id, bit_id, true)) {
                bitExists = false;
            }
        }


        DailyBit dailyBit = new DailyBit(0L,false, dailyBitDTO.getDate(), bit, client);

        dailyBit = dailyBitRepository.save(dailyBit);
        return dailyBit;
    }

    @Override
    public DailyBitResponseDTO getDailyBitResponseById(Long client_id, LocalDate date) {
        DailyBit dailyBit = dailyBitRepository.getDailyBitByClientIdAndDate(client_id,date);
        Bit bit = bitService.getBitById(dailyBit.getBit().getId());
        DailyBitResponseDTO dailyBitResponseDTO = new DailyBitResponseDTO(dailyBit.getId(),
                dailyBit.getView(), dailyBit.getDate(),bit.getTitle(), bit.getContent(), bit.getImage());
        return dailyBitResponseDTO;
    }



    @Override
    public DailyBit getDailyBitById(Long id) {
        DailyBit dailyBit = dailyBitRepository.findById(id).orElse(null);
        if(dailyBit == null){
            throw new ResourceNotFoundException("Daily Bit no encontrado");
        }
        return dailyBit;
    }

    private Long generateUniqueBitId() {
        Integer lastId = bitRepository.findAll().size();
        Long randomId = (long) (Math.random() * lastId-1) + 1;
        return randomId;
    }

    @Override
    public DailyBit updateDailyBit(Long id) {
        DailyBit dailyBitfound = getDailyBitById(id);
        dailyBitfound.setView(true);
        return dailyBitRepository.save(dailyBitfound);
    }
}
