package com.wisecoin.LlamaPay_Api.controllers;

import com.wisecoin.LlamaPay_Api.dtos.ClientDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.ClientRequestDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.ClientHasPremiunDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.ClientResponseDTO;
import com.wisecoin.LlamaPay_Api.entities.Client;
import com.wisecoin.LlamaPay_Api.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class ClientController {
    @Autowired
    ClientService clientService;

    @GetMapping("/clients")
    public ResponseEntity<List<Client>> listAllClients(){
        return new ResponseEntity<List<Client>>(clientService.listAll(),
                HttpStatus.OK);
    }

    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable("id") Long id, @Valid @RequestBody ClientRequestDTO clientRequestDTO) {
        Client clientUpdate = clientService.updateClient(id,clientRequestDTO);
        if (clientUpdate!=null) {
            return new ResponseEntity<>(clientUpdate, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @GetMapping("/clients/{id}")
    public ResponseEntity<ClientResponseDTO> getClientResponseDTOById(@PathVariable("id") Long id){
        return new ResponseEntity<ClientResponseDTO>(clientService.getClientResponseById(id),
                HttpStatus.OK);
    }
    @GetMapping("/clients/premiun/{id}")
    public ResponseEntity<ClientHasPremiunDTO> getClientPremiunById(@PathVariable("id") Long id){
        return new ResponseEntity<ClientHasPremiunDTO>(clientService.getClientPremiunById(id),
                HttpStatus.OK);
    }

    @GetMapping("/clients/user/{userId}")
    public ResponseEntity<Long> getClientIdByUserId(@PathVariable("userId") Long userId){
        return new ResponseEntity<Long>(clientService.getClientIdByUserId(userId),
                HttpStatus.OK);
    }
}
