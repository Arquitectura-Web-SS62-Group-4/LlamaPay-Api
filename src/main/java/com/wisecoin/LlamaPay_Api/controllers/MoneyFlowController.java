package com.wisecoin.LlamaPay_Api.controllers;

import com.wisecoin.LlamaPay_Api.dtos.MoneyFlowDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.MoneyFlowRequestDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.MoneyFlowCategoryDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.MoneyFlowResponseDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.MoneyFlowSummaryDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.MoneyFlowTypeDTO;
import com.wisecoin.LlamaPay_Api.entities.MoneyFlow;
import com.wisecoin.LlamaPay_Api.services.MoneyFlowService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
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

    @GetMapping("/moneyFlows/client/{idClient}/{type}/year/{year}/month/{month}")
    public ResponseEntity<List<MoneyFlowCategoryDTO>> getMoneyFlowExpensesByMonth(@PathVariable("idClient") Long idClient, @PathVariable("type") String type, @PathVariable("year") int year, @PathVariable("month") int month){
        return new ResponseEntity<List<MoneyFlowCategoryDTO>>(moneyFlowService.getMoneyFlowByTypeAndMonth(idClient,type,year,month),
                HttpStatus.OK);
    }

    @GetMapping("/moneyFlows/client/{idClient}/type/{type}/year/{year}/month/{month}")
    public ResponseEntity<List<MoneyFlowTypeDTO>> getMoneyFlowTypeAndMonth(@PathVariable("idClient") Long idClient, @PathVariable("type") String type, @PathVariable("year") int year, @PathVariable("month") int month){
        return new ResponseEntity<List<MoneyFlowTypeDTO>>(moneyFlowService.getListByTypeAndMonth(idClient, type ,year,month),
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

    @GetMapping("/moneyFlows/client/{idClient}/Total/type/{type}/year/{year}/month/{month}")
    public ResponseEntity<MoneyFlowTypeDTO> getTotalByTypeAndMonth(@PathVariable("idClient") Long idClient,@PathVariable("type") String type ,@PathVariable("year") int year, @PathVariable("month") int month){
        return new ResponseEntity<MoneyFlowTypeDTO>(moneyFlowService.getTotalByTypeAndMonth(idClient,type,year,month),
                HttpStatus.OK);
    }

    @GetMapping("/moneyFlows/client/{id}/year/{year}/month/{month}")
    public ResponseEntity<List<MoneyFlowResponseDTO>> listMoneyFlowsByClient(@PathVariable("id") Long id, @PathVariable("year") Long year, @PathVariable("month") Long month){
        return new ResponseEntity<List<MoneyFlowResponseDTO>>(moneyFlowService.findByClient(id, year, month),
                HttpStatus.OK);
    }

    @GetMapping("/moneyFlows/{id}")
    public ResponseEntity<MoneyFlowResponseDTO> getMoneyFlowById(@PathVariable("id") Long id){
        return new ResponseEntity<MoneyFlowResponseDTO>(moneyFlowService.getMoneyFlowResponseById(id),
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

    @PutMapping("/moneyFlows/{id}/category/{categoryId}")
    public ResponseEntity<MoneyFlow> updateGoal(@PathVariable("id") Long id, @PathVariable("categoryId") Long categoryId,
                                           @Valid @RequestBody MoneyFlowRequestDTO moneyFlowRequestDto){
        MoneyFlow moneyFlowUpdate = moneyFlowService.updateMoneyFlow(id,categoryId,moneyFlowRequestDto);
        if (moneyFlowUpdate!=null) {
            return new ResponseEntity<>(moneyFlowUpdate, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
