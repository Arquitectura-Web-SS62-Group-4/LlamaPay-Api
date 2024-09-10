package com.wisecoin.LlamaPay_Api.repositories;

import com.wisecoin.LlamaPay_Api.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface ClientRepository extends JpaRepository<Client,Long> {
    public boolean existsById(Long id);
    public boolean existsByEmail(String email);
    public boolean existsByPhoneNumber(String phoneNumber);

    @Query(nativeQuery = true, value = "SELECT c.id FROM clients c WHERE c.email = :email")
    public Long getIdByEmail(@Param("email") String email);
}
