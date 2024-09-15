package com.wisecoin.LlamaPay_Api.services;

import com.wisecoin.LlamaPay_Api.dtos.MoneyFlowDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.MoneyFlowRequestDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.MoneyFlowResponseDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.MoneyFlowSummaryDTO;
import com.wisecoin.LlamaPay_Api.entities.MoneyFlow;

import java.util.List;

public interface MoneyFlowService {
    public MoneyFlow addMoneyFlow(Long clientId, Long categoryId, MoneyFlowDTO moneyFlowDto);
    public List<MoneyFlow> findByClient(Long clientId);
    public List<MoneyFlow> listAll();
    public void deleteMoneyFlow(Long id);
    public MoneyFlow getMoneyFlowById(Long id);
    public MoneyFlow updateMoneyFlow(Long id, Long categoryId,MoneyFlowRequestDTO moneyFlowRequestDto);

    public List<MoneyFlowResponseDTO> getMoneyFlowByTypeAndMonth(String type, int month);
    public List<MoneyFlowSummaryDTO> getMoneyFlowNeto(int firstMonth, int finalMonth);
}
