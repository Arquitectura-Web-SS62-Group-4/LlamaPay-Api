package com.wisecoin.LlamaPay_Api.controllers;

import com.wisecoin.LlamaPay_Api.dtos.DailyBitDTO;
import com.wisecoin.LlamaPay_Api.dtos.MoneyFlowDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.DailyBitRequestDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.MoneyFlowRequestDTO;
import com.wisecoin.LlamaPay_Api.entities.DailyBit;
import com.wisecoin.LlamaPay_Api.entities.MoneyFlow;
import com.wisecoin.LlamaPay_Api.services.DailyBitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class DailyBitController {

    @Autowired
    DailyBitService dailyBitService;

    @GetMapping("/dailyBits")
    public ResponseEntity<List<DailyBit>> listAllDailyBit(){
        return new ResponseEntity<List<DailyBit>>(dailyBitService.listAll(),
                HttpStatus.OK);
    }

    @GetMapping("/dailyBits/client/{id}")
    public ResponseEntity<List<DailyBit>> listDailyBitByClient( @PathVariable("id") Long id){
        return new ResponseEntity<List<DailyBit>>(dailyBitService.findByClient(id),
                HttpStatus.OK);
    }

    @PostMapping("/dailyBits/client/{id}/typeBit/{typeBitId}")
    public ResponseEntity<DailyBit> addDailyBit(@PathVariable("id") Long id, @PathVariable("typeBitId") Long typeBitId,
                                                  @Valid @RequestBody DailyBitDTO dailyBitDTO){
        DailyBit DailyBitCreated = dailyBitService.addDailyBit(id, typeBitId, dailyBitDTO);
        return new ResponseEntity<DailyBit>(DailyBitCreated,
                HttpStatus.OK);
    }

    @DeleteMapping("/dailyBits/{id}")
    public ResponseEntity<HttpStatus> deleteDailyBit(@PathVariable("id") Long id){
        dailyBitService.deleteDailyBit(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/dailyBits/client/{id}/typeBit/{typeBitId}")
    public ResponseEntity<DailyBit> updateDailyBit(@PathVariable("id") Long id, @PathVariable("typeBitId") Long typeBitId,
                                                @Valid @RequestBody DailyBitRequestDTO dailyBitRequestDTO){
        DailyBit dailyBitUpdate = dailyBitService.updateDailyBit(id,typeBitId,dailyBitRequestDTO);
        if (dailyBitUpdate!=null) {
            return new ResponseEntity<>(dailyBitUpdate, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
