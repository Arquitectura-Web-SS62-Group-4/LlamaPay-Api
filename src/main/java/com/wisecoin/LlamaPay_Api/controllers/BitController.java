package com.wisecoin.LlamaPay_Api.controllers;

import com.wisecoin.LlamaPay_Api.dtos.BitDTO;
import com.wisecoin.LlamaPay_Api.entities.Bit;
import com.wisecoin.LlamaPay_Api.services.BitService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class BitController {
    @Autowired
    BitService bitService;

    @PostMapping("/bits")
    public ResponseEntity<Bit> addBit(@Valid @RequestBody BitDTO bitDTO){
        Bit bitCreated = bitService.addBit(bitDTO);
        return new ResponseEntity<Bit>(bitCreated,
                HttpStatus.OK);
    }

    @GetMapping("/bits/{id}")
    public ResponseEntity<Bit> getBit(@PathVariable("id") Long id){
        return new ResponseEntity<Bit>(bitService.getBitById(id),HttpStatus.OK);
    }
}
