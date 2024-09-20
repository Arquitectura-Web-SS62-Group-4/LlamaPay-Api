package com.wisecoin.LlamaPay_Api.controllers;

import com.wisecoin.LlamaPay_Api.entities.Premiun;
import com.wisecoin.LlamaPay_Api.services.PremiunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PremiunController{

    @Autowired
    PremiunService premiunService;

    @GetMapping("/premiuns")
    public ResponseEntity<List<Premiun>> findAll(){
        return new ResponseEntity<List<Premiun>>(premiunService.findALl(),
                HttpStatus.OK);
    }
}
