package com.wisecoin.LlamaPay_Api.serviceImpl;

import com.wisecoin.LlamaPay_Api.dtos.DailyBitDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.DailyBitRequestDTO;
import com.wisecoin.LlamaPay_Api.entities.*;
import com.wisecoin.LlamaPay_Api.exceptions.ResourceNotFoundException;
import com.wisecoin.LlamaPay_Api.exceptions.ValidationException;
import com.wisecoin.LlamaPay_Api.repositories.ClientRepository;
import com.wisecoin.LlamaPay_Api.repositories.DailyBitRepository;
import com.wisecoin.LlamaPay_Api.services.DailyBitService;
import com.wisecoin.LlamaPay_Api.services.TypeBitService;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

public class DailyBitServiceImpl implements DailyBitService {

    @Autowired
    DailyBitRepository dailyBitRepository;

    @Autowired
    TypeBitService typeBitService;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public DailyBit addDailyBit(Long client_id, Long typebit_id, DailyBitDTO dailyBitDTO) {
        Client client = clientRepository.findById(client_id).orElse(null);
        if (client==null) {
            throw new ResourceNotFoundException("Cliente no encontrado");
        }
        LocalDate today = LocalDate.now();

        if(dailyBitDTO.getTitle().length()>40){
            throw new ValidationException("El titulo no puede tener más de 40 caracteres");
        }

        if(dailyBitDTO.getContent().length()>200){
            throw new ValidationException("El contenido no puede tener más de 200 caracteres");
        }

        if(dailyBitDTO.getDate().isBefore(today)){
            throw new ValidationException("La fecha no puede ser anterior a la actual");
        }

        TypeBit typeBit=typeBitService.getTypeBitById(typebit_id);

        DailyBit dailyBit=new DailyBit(dailyBitDTO.getId(), dailyBitDTO.getTitle(), dailyBitDTO.getContent(),
                dailyBitDTO.getDate(),typeBit,client);

        return dailyBitRepository.save(dailyBit);

    }

    @Override
    public List<DailyBit> findByClient(Long client_id) {
        Client client = clientRepository.findById(client_id).orElse(null);
        if(client==null){
            throw new ValidationException("Cliente no encontrado");
        }
        return dailyBitRepository.findByClient(client);
    }

    @Override
    public List<DailyBit> listAll() {
        return dailyBitRepository.findAll();
    }

    @Override
    public void deleteDailyBit(Long id) {
        DailyBit deleteDailyBit = getDailyBitById(id);
        dailyBitRepository.deleteById(id);
    }

    @Override
    public DailyBit getDailyBitById(Long id) {
        DailyBit dailyBit = dailyBitRepository.findById(id).orElse(null);
        if (dailyBit==null) {
            throw new ResourceNotFoundException("DailyBit no encontrado");
        }
        return dailyBit;
    }

    @Override
    public DailyBit updateDailyBit(Long id, Long typebit_id, DailyBitRequestDTO dailyBitRequestDTO) {
        DailyBit DailyBitfound = getDailyBitById(id);
        if(DailyBitfound!=null){
            LocalDate today = LocalDate.now();

            if(dailyBitRequestDTO.getContent()!=null){
                if(dailyBitRequestDTO.getContent().length()>200){
                    throw new ValidationException("El contenido no puede tener más de 200 caracteres");
                }
                DailyBitfound.setContent(dailyBitRequestDTO.getContent());
            }

            if(dailyBitRequestDTO.getDate()!=null){
                if(dailyBitRequestDTO.getDate().isBefore(today)){
                    throw new ValidationException("La fecha no puede ser anterior a la actual");
                }
                DailyBitfound.setDate(dailyBitRequestDTO.getDate());
            }

            if(!dailyBitRequestDTO.getTitle().isBlank()){
                if(dailyBitRequestDTO.getTitle().length()>40){
                    throw new ValidationException("El titulo no puede tener más de 40 caracteres");
                }
                DailyBitfound.setTitle(dailyBitRequestDTO.getTitle());
            }

            return dailyBitRepository.save(DailyBitfound);
        }

        if(!dailyBitRepository.existsById(id)){
            throw new ResourceNotFoundException("DailyBit no encontrado");
        }
        return null;
    }
}
