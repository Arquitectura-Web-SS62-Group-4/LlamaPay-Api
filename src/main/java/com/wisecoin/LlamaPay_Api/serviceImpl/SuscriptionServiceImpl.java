package com.wisecoin.LlamaPay_Api.serviceImpl;

import com.wisecoin.LlamaPay_Api.entities.Client;
import com.wisecoin.LlamaPay_Api.entities.Premiun;
import com.wisecoin.LlamaPay_Api.entities.Suscription;
import com.wisecoin.LlamaPay_Api.exceptions.ValidationException;
import com.wisecoin.LlamaPay_Api.repositories.ClientRepository;
import com.wisecoin.LlamaPay_Api.repositories.SuscriptionRepository;
import com.wisecoin.LlamaPay_Api.services.ClientService;
import com.wisecoin.LlamaPay_Api.services.PremiunService;
import com.wisecoin.LlamaPay_Api.services.SuscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SuscriptionServiceImpl implements SuscriptionService {

    @Autowired
    SuscriptionRepository suscriptionRepository;

    @Autowired
    PremiunService premiunService;

    @Autowired
    ClientService clientService;

    @Autowired
    ClientRepository clientRepository;

    @Override
    public Suscription addSuscription(Long clientId, Long premiunId) {
        Client client = clientService.getClientById(clientId);
        if(client.getHasPremiun()){
            throw new ValidationException("El cliente cuenta con un suscripcion vigente");
        }
        client.setHasPremiun(true);
        clientRepository.save(client);
        Premiun premiun = premiunService.findById(premiunId);
        LocalDate startTime = LocalDate.now();
        LocalDate endTime = startTime.plusDays(30);
        Suscription suscription = new Suscription(0L, startTime,endTime,client,premiun);
        return suscriptionRepository.save(suscription);
    }

    @Override
    public List<Suscription> findByClient(Long clientId) {
        Client client = clientService.getClientById(clientId);
        List<Suscription> list = suscriptionRepository.findByClient(client);
        return list;
    }
}
