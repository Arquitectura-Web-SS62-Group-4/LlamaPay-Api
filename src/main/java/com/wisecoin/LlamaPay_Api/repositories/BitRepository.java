package com.wisecoin.LlamaPay_Api.repositories;

import com.wisecoin.LlamaPay_Api.entities.Bit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BitRepository extends JpaRepository<Bit, Long> {
}
