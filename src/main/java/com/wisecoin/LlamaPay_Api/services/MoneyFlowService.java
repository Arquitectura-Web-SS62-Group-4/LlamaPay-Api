package com.wisecoin.LlamaPay_Api.services;

import com.wisecoin.LlamaPay_Api.dtos.MoneyFlowDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.MoneyFlowRequestDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.MoneyFlowCategoryDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.MoneyFlowResponseDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.MoneyFlowSummaryDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.MoneyFlowTypeDTO;
import com.wisecoin.LlamaPay_Api.entities.MoneyFlow;

import java.time.LocalDate;
import java.util.List;

public interface MoneyFlowService {
    public MoneyFlow addMoneyFlow(Long clientId, Long categoryId, MoneyFlowDTO moneyFlowDto);
    public List<MoneyFlowResponseDTO> findByClient(Long clientId, Long year, Long month);
    public List<MoneyFlow> listAll();
    public void deleteMoneyFlow(Long id);
    public MoneyFlow getMoneyFlowById(Long id);
    public MoneyFlowResponseDTO getMoneyFlowResponseById(Long id);
    public MoneyFlow updateMoneyFlow(Long id, Long categoryId,MoneyFlowRequestDTO moneyFlowRequestDto);

    public List<MoneyFlowCategoryDTO> getMoneyFlowByTypeAndMonth(Long idClient, String type, int year, int month);
    public List<MoneyFlowSummaryDTO> getMoneyFlowNeto(Long idClient, int year, int firstMonth, int finalMonth);

    public MoneyFlowSummaryDTO getMoneyFlowNetoByMonth(Long idClient, int year, int month);

    public Double getMoneyFlowNetoByRange(Long idClient, LocalDate startDate, LocalDate endDate);
    public List<MoneyFlowTypeDTO> getListByTypeAndMonth (Long idClient, String type, int year, int month);
    public MoneyFlowTypeDTO getTotalByTypeAndMonth (Long idClient, String type, int year, int month);
}
