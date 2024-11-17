package com.wisecoin.LlamaPay_Api.repositories;

import com.wisecoin.LlamaPay_Api.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ClientRepository extends JpaRepository<Client,Long> {
    public boolean existsById(Long id);
    public boolean existsByEmail(String email);
    public boolean existsByPhoneNumber(String phoneNumber);
    public List<Client> findByEmail(String email);
    public List<Client> findByPhoneNumber(String phoneNumber);

    @Query(nativeQuery = true, value = "" +
            "SELECT * FROM clients c WHERE c.user_id = :userId")
    public Client getClientByUserId(@Param("userId") Long userId);
}
