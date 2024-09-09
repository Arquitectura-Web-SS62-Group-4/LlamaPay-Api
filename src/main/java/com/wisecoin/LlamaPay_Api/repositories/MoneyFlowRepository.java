package com.wisecoin.LlamaPay_Api.repositories;

import com.wisecoin.LlamaPay_Api.entities.Client;
import com.wisecoin.LlamaPay_Api.entities.MoneyFlow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MoneyFlowRepository extends JpaRepository<MoneyFlow,Long> {
    public List<MoneyFlow> findByClient(Client client);
    public List<MoneyFlow> findByType(String type);
    public boolean existsByName(String name);
}
