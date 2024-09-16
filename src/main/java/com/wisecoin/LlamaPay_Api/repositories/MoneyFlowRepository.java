package com.wisecoin.LlamaPay_Api.repositories;

import com.wisecoin.LlamaPay_Api.dtos.MoneyFlowDTO;
import com.wisecoin.LlamaPay_Api.entities.Client;
import com.wisecoin.LlamaPay_Api.entities.MoneyFlow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MoneyFlowRepository extends JpaRepository<MoneyFlow,Long> {
    public List<MoneyFlow> findByClient(Client client);
    public List<MoneyFlow> findByType(String type);
    public boolean existsByName(String name);

    @Query(nativeQuery = true, value = "" +
            "SELECT * FROM money_flows m " +
            "WHERE m.type = :type " +
            "AND EXTRACT(MONTH FROM m.date) = :month ")
    public List<MoneyFlow> getListByTypeAndMonth(@Param("type") String type,
                                                    @Param("month") int month);

    @Query(nativeQuery = true, value = "" +
            "SELECT * FROM money_flows m " +
            "WHERE EXTRACT(MONTH FROM m.date) BETWEEN :firstMonth AND :finalMonth")
    public List<MoneyFlow> getListByRange(@Param("firstMonth") int firstMonth,
                                          @Param("finalMonth") int finalMonth);

    //
    @Query(nativeQuery = true, value = "" +
            "SELECT * FROM money_flows m " +
            "WHERE EXTRACT(MONTH FROM m.date) = :month")
    public List<MoneyFlow> getListByMonth(@Param("month") int month);
}
