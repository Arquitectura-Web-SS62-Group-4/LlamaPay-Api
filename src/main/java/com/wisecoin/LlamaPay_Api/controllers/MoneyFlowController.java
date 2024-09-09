package com.wisecoin.LlamaPay_Api.controllers;

import com.wisecoin.LlamaPay_Api.dtos.GoalDTO;
import com.wisecoin.LlamaPay_Api.dtos.MoneyFlowDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.GoalRequestDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.MoneyFlowRequestDTO;
import com.wisecoin.LlamaPay_Api.entities.Goal;
import com.wisecoin.LlamaPay_Api.entities.MoneyFlow;
import com.wisecoin.LlamaPay_Api.services.MoneyFlowService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class MoneyFlowController {
    @Autowired
    MoneyFlowService moneyFlowService;

    @GetMapping("/moneyFlows")
    public ResponseEntity<List<MoneyFlow>> listAllMoneyFlows(){
        return new ResponseEntity<List<MoneyFlow>>(moneyFlowService.listAll(),
                HttpStatus.OK);
    }

    @GetMapping("/moneyFlows/user/{id}")
    public ResponseEntity<List<MoneyFlow>> listMoneyFlowsByClient( @PathVariable("id") Long id){
        return new ResponseEntity<List<MoneyFlow>>(moneyFlowService.findByClient(id),
                HttpStatus.OK);
    }

    @PostMapping("/moneyFlows/user/{id}/category/{categoryId}")
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

    @PutMapping("/moneyFlows/user/{id}/category/{categoryId}")
    public ResponseEntity<MoneyFlow> updateGoal(@PathVariable("id") Long id, @PathVariable("categoryId") Long categoryId,
                                           @Valid @RequestBody MoneyFlowRequestDTO moneyFlowRequestDto){
        MoneyFlow moneyFlowUpdate = moneyFlowService.updateMoneyFlow(id,categoryId,moneyFlowRequestDto);
        if (moneyFlowUpdate!=null) {
            return new ResponseEntity<>(moneyFlowUpdate, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
