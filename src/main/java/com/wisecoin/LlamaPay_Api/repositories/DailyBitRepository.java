package com.wisecoin.LlamaPay_Api.repositories;

import com.wisecoin.LlamaPay_Api.entities.DailyBit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface DailyBitRepository extends JpaRepository<DailyBit,Long> {
    public boolean existsByClient_idAndBit_idAndView(Long client_id, Long bit_id, Boolean view);

    @Query(nativeQuery = true, value = "" +
            "SELECT * FROM daily_bits d " +
            "WHERE d.date = :date AND d.client_id = :client_id")
    public DailyBit getDailyBitByClientIdAndDate(@Param("client_id") Long client_id, @Param("date") LocalDate date);

}
