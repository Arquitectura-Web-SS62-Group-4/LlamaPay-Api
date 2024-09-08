package com.wisecoin.LlamaPay_Api.services;

import com.wisecoin.LlamaPay_Api.dtos.ClientDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.ClientRequestDTO;
import com.wisecoin.LlamaPay_Api.entities.Client;

import java.util.List;

public interface ClientService {
    public Client addClient(ClientDTO clientDto);
    public List<Client> listAll();
    public void deleteClient(Long id);
    public Client getClientById(Long id);
    public Client updateClient(Long id, ClientRequestDTO clientRequestDTO);
}
