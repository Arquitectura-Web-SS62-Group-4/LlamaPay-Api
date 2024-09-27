package com.wisecoin.LlamaPay_Api.controllers;

import com.wisecoin.LlamaPay_Api.dtos.MoneyFlowDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.MoneyFlowRequestDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.MoneyFlowResponseDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.MoneyFlowSummaryDTO;
import com.wisecoin.LlamaPay_Api.entities.MoneyFlow;
import com.wisecoin.LlamaPay_Api.services.MoneyFlowService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MoneyFlowController {
    @Autowired
    MoneyFlowService moneyFlowService;

    @GetMapping("/moneyFlows/client/{idClient}/NetAmount/year/{year}/month/{month}")
    public ResponseEntity<MoneyFlowSummaryDTO> getMoneyFlowAmountByMonth(@PathVariable("idClient") Long idClient,@PathVariable("year") int year, @PathVariable("month") int month){
        return new ResponseEntity<MoneyFlowSummaryDTO>(moneyFlowService.getMoneyFlowNetoByMonth(idClient,year,month),
                HttpStatus.OK);
    }

    @GetMapping("/moneyFlows/client/{idClient}/Expenses/year/{year}/month/{month}")
    public ResponseEntity<List<MoneyFlowResponseDTO>> getMoneyFlowExpensesByMonth(@PathVariable("idClient") Long idClient, @PathVariable("year") int year, @PathVariable("month") int month){
        return new ResponseEntity<List<MoneyFlowResponseDTO>>(moneyFlowService.getMoneyFlowByTypeAndMonth(idClient, "Gasto",year,month),
                HttpStatus.OK);
    }

    @GetMapping("/moneyFlows/client/{idClient}/Incomes/year/{year}/month/{month}")
    public ResponseEntity<List<MoneyFlowResponseDTO>> getMoneyFlowIncomesByMonth(@PathVariable("idClient") Long idClient, @PathVariable("year") int year, @PathVariable("month") int month){
        return new ResponseEntity<List<MoneyFlowResponseDTO>>(moneyFlowService.getMoneyFlowByTypeAndMonth(idClient,"Ingreso",year, month),
                HttpStatus.OK);
    }

    @GetMapping("/moneyFlows/client/{idClient}/summary/year/{year}/firstMonth/{firstMonth}/finalMonth/{finalMonth}")
    public ResponseEntity<List<MoneyFlowSummaryDTO>> getSummary(@PathVariable("idClient") Long idClient, @PathVariable("year") int year, @PathVariable("firstMonth") int firstMonth, @PathVariable("finalMonth") int finalMonth){
        return new ResponseEntity<List<MoneyFlowSummaryDTO>>(moneyFlowService.getMoneyFlowNeto(idClient, year,firstMonth,finalMonth),
                HttpStatus.OK);
    }

    @GetMapping("/moneyFlows")
    public ResponseEntity<List<MoneyFlow>> listAllMoneyFlows(){
        return new ResponseEntity<List<MoneyFlow>>(moneyFlowService.listAll(),
                HttpStatus.OK);
    }

    @GetMapping("/moneyFlows/client/{id}")
    public ResponseEntity<List<MoneyFlow>> listMoneyFlowsByClient( @PathVariable("id") Long id){
        return new ResponseEntity<List<MoneyFlow>>(moneyFlowService.findByClient(id),
                HttpStatus.OK);
    }

    @PostMapping("/moneyFlows/client/{id}/category/{categoryId}")
    public ResponseEntity<MoneyFlow> addMoneyFlow(@PathVariable("id") Long id, @PathVariable("categoryId") Long categoryId,
                                             @Valid @RequestBody MoneyFlowDTO moneyFlowDto){
        MoneyFlow moneyFlowCreated = moneyFlowService.addMoneyFlow(id, categoryId, moneyFlowDto);
        return new ResponseEntity<MoneyFlow>(moneyFlowCreated,
                HttpStatus.OK);
    }

    @DeleteMapping("/moneyFlows/{id}")
    public ResponseEntity<HttpStatus> deleteMoneyFlow(@PathVariable("id") Long id){
        moneyFlowService.deleteMoneyFlow(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/moneyFlows/client/{id}/category/{categoryId}")
    public ResponseEntity<MoneyFlow> updateGoal(@PathVariable("id") Long id, @PathVariable("categoryId") Long categoryId,
                                           @Valid @RequestBody MoneyFlowRequestDTO moneyFlowRequestDto){
        MoneyFlow moneyFlowUpdate = moneyFlowService.updateMoneyFlow(id,categoryId,moneyFlowRequestDto);
        if (moneyFlowUpdate!=null) {
            return new ResponseEntity<>(moneyFlowUpdate, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
