package com.wisecoin.LlamaPay_Api.services;

import com.wisecoin.LlamaPay_Api.entities.Suscription;

import java.util.List;

public interface SuscriptionService {
    public Suscription addSuscription(Long clientId, Long premiunId);
    public List<Suscription> findByClient(Long clientId);
}