package com.wisecoin.LlamaPay_Api.services;

import com.wisecoin.LlamaPay_Api.dtos.ClientDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.ClientRequestDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.ClientHasPremiunDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.ClientResponseDTO;
import com.wisecoin.LlamaPay_Api.entities.Client;
import com.wisecoin.LlamaPay_Api.entities.User;

import java.util.List;

public interface ClientService {
    public Client addClient(ClientDTO clientDto, User user);
    public List<Client> listAll();
    public void deleteClient(Long id);
    public Client getClientById(Long id);
    public Client updateClient(Long id, ClientRequestDTO clientRequestDTO);
    public ClientResponseDTO getClientResponseById(Long id);
    public ClientHasPremiunDTO getClientPremiunById(Long id);
    public Long getClientIdByUserId(Long userId);
}
