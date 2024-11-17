package com.wisecoin.LlamaPay_Api.controllers;

import com.wisecoin.LlamaPay_Api.dtos.DailyBitDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.DailyBitResponseDTO;
import com.wisecoin.LlamaPay_Api.entities.DailyBit;
import com.wisecoin.LlamaPay_Api.services.DailyBitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class DailyBitController {

    @Autowired
    DailyBitService dailyBitService;

    @GetMapping("/dailyBits/client/{id}/date/{date}")
    public ResponseEntity<DailyBitResponseDTO> listDailyBitById(@PathVariable("id") Long id, @PathVariable("date") LocalDate date){
        return new ResponseEntity<DailyBitResponseDTO>(dailyBitService.getDailyBitResponseById(id,date),
                HttpStatus.OK);
    }

    @PostMapping("/dailyBits/client/{id}")
    public ResponseEntity<DailyBit> addDailyBit(@PathVariable("id") Long id, @Valid @RequestBody DailyBitDTO dailyBitDTO){
        DailyBit DailyBitCreated = dailyBitService.addDailyBit(id,dailyBitDTO);
        return new ResponseEntity<DailyBit>(DailyBitCreated,
                HttpStatus.OK);
    }

    @PutMapping("/dailyBits/{id}")
    public ResponseEntity<DailyBit> updateDailyBit(@PathVariable("id") Long id){
        DailyBit dailyBitUpdate = dailyBitService.updateDailyBit(id);
        if (dailyBitUpdate!=null) {
            return new ResponseEntity<>(dailyBitUpdate, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
