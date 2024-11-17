package com.wisecoin.LlamaPay_Api.controllers;

import com.wisecoin.LlamaPay_Api.entities.Premiun;
import com.wisecoin.LlamaPay_Api.services.PremiunService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class PremiunController{

    @Autowired
    PremiunService premiunService;

    @GetMapping("/premiuns")
    public ResponseEntity<Premiun> getPremiun(){
        return new ResponseEntity<Premiun>(premiunService.findById(1L),
                HttpStatus.OK);
    }
}
