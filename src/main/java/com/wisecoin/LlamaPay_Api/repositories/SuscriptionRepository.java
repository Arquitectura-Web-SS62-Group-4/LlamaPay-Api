package com.wisecoin.LlamaPay_Api.repositories;

import com.wisecoin.LlamaPay_Api.entities.Client;
import com.wisecoin.LlamaPay_Api.entities.Suscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuscriptionRepository extends JpaRepository<Suscription,Long> {
    public List<Suscription> findByClient(Client client);
}
