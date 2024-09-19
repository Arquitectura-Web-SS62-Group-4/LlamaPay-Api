package com.wisecoin.LlamaPay_Api.repositories;

import com.wisecoin.LlamaPay_Api.entities.Client;
import com.wisecoin.LlamaPay_Api.entities.DailyBit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DailyBitRepository extends JpaRepository<DailyBit,Long> {
    public List<DailyBit> findByClient(Client client);
    public List<DailyBit> findByTypeId(String type_id);
    public boolean existsByTitle(String title);
}
