package com.wisecoin.LlamaPay_Api.repositories;

import com.wisecoin.LlamaPay_Api.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ClientRepository extends JpaRepository<Client,Long> {
    public boolean existsById(Long id);
    public boolean existsByEmail(String email);
    public boolean existsByPhoneNumber(String phoneNumber);
}
