package com.wisecoin.LlamaPay_Api.services;

import com.wisecoin.LlamaPay_Api.dtos.CardDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.CardRequestDTO;
import com.wisecoin.LlamaPay_Api.entities.Card;

import java.time.format.DateTimeFormatter;
import java.util.List;

public interface CardService {
    public Card addCard(Long ClientId, CardDTO cardDto);
    public Card findByClient(Long clientId);
    public List<Card> listAll();
    public void deleteCard(Long id);
    public Card getCardById(Long id);
    public Card updateCard(Long id, CardRequestDTO cardRequestDto);
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM-yyyy");
}
