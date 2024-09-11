package com.wisecoin.LlamaPay_Api.repositories;

import com.wisecoin.LlamaPay_Api.entities.Client;
import com.wisecoin.LlamaPay_Api.entities.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface
GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByClient(Client client);
    public boolean existsByName(String name);
}
