package com.wisecoin.LlamaPay_Api.controllers;

import com.wisecoin.LlamaPay_Api.dtos.response.TypeBitResponseDTO;
import com.wisecoin.LlamaPay_Api.services.TypeBitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api")
@RestController
public class TypeBitController {
    @Autowired
    TypeBitService typeBitService;

    @GetMapping("/tipos")
    public ResponseEntity<List<TypeBitResponseDTO>> listAllTipos(){
        return new ResponseEntity<List<TypeBitResponseDTO>>(typeBitService.listAll(),
                HttpStatus.OK);
    }
}
