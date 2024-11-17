package com.wisecoin.LlamaPay_Api.repositories;

import com.wisecoin.LlamaPay_Api.entities.Card;
import com.wisecoin.LlamaPay_Api.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    public Card findByClient(Client client);
    public boolean existsByCardNumber(String cardNumber);
    public List<Card> findByCardNumber(String cardNumber);
}
