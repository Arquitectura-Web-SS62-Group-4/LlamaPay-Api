package com.wisecoin.LlamaPay_Api.repositories;

import com.wisecoin.LlamaPay_Api.dtos.MoneyFlowDTO;
import com.wisecoin.LlamaPay_Api.entities.Client;
import com.wisecoin.LlamaPay_Api.entities.MoneyFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MoneyFlowRepository extends JpaRepository<MoneyFlow,Long> {
    public List<MoneyFlow> findByClient(Client client);
    public List<MoneyFlow> findByType(String type);
    public List<MoneyFlow> findByNameAndClient_id(String name, Long client_id);
    public boolean existsByNameAndClient_id(String name, Long client_id);

    @Query(nativeQuery = true, value = "" +
            "SELECT * FROM money_flows m " +
            "WHERE m.type = :type " +
            "AND EXTRACT(MONTH FROM m.date) = :month " +
            "AND EXTRACT(YEAR FROM m.date) = :year")
    public List<MoneyFlow> getListByTypeAndMonth(@Param("type") String type, @Param("month") int month, @Param("year") int year);

    @Query(nativeQuery = true, value = "" +
            "SELECT * FROM money_flows m " +
            "WHERE EXTRACT(MONTH FROM m.date) BETWEEN :firstMonth AND :finalMonth " +
            "AND EXTRACT(YEAR FROM m.date) = :year")
    public List<MoneyFlow> getListByRange(@Param("firstMonth") int firstMonth,
                                          @Param("finalMonth") int finalMonth, @Param("year") int year);

    @Query(nativeQuery = true, value = "" +
            "SELECT * FROM money_flows m " +
            "WHERE EXTRACT(MONTH FROM m.date) = :month " +
            "AND EXTRACT(YEAR FROM m.date) = :year")
    public List<MoneyFlow> getListByMonth(@Param("month") int month, @Param("year") int year);

    @Query(nativeQuery = true, value = "" +
            "SELECT * FROM money_flows m " +
            "WHERE m.date BETWEEN :startDate AND :endDate")
    public List<MoneyFlow> getListByDateRange(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
