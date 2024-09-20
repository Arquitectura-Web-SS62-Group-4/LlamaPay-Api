package com.wisecoin.LlamaPay_Api.controllers;

import com.wisecoin.LlamaPay_Api.entities.Suscription;
import com.wisecoin.LlamaPay_Api.services.SuscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SuscriptionController {

    @Autowired
    SuscriptionService suscriptionService;

    @PostMapping("/suscriptions/client/{id}/premiun/{premiun_id}")
    public ResponseEntity<Suscription> addSuscription (@PathVariable("id") Long id, @PathVariable("premiun_id") Long premiun_id){
        return new ResponseEntity<Suscription>(suscriptionService.addSuscription(id,premiun_id),
                HttpStatus.CREATED);
    }

    @GetMapping("/suscriptions/client/{id}")
    public ResponseEntity<List<Suscription>> getSuscriptionsById (@PathVariable("id") Long id){
        return new ResponseEntity<List<Suscription>>(suscriptionService.findByClient(id),
                HttpStatus.OK);
    }
}
