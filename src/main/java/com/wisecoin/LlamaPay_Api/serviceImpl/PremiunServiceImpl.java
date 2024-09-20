package com.wisecoin.LlamaPay_Api.serviceImpl;

import com.wisecoin.LlamaPay_Api.entities.Premiun;
import com.wisecoin.LlamaPay_Api.exceptions.ResourceNotFoundException;
import com.wisecoin.LlamaPay_Api.repositories.PremiunRepository;
import com.wisecoin.LlamaPay_Api.services.PremiunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PremiunServiceImpl implements PremiunService {

    @Autowired
    PremiunRepository premiunRepository;

    @Override
    public Premiun findById(Long id) {
        Premiun premiun = premiunRepository.findById(id).orElse(null);
        if(premiun == null){
            throw new ResourceNotFoundException("Premiun no encontrado");
        }
        return premiun;
    }

    @Override
    public List<Premiun> findALl() {
        return premiunRepository.findAll();
    }

}
