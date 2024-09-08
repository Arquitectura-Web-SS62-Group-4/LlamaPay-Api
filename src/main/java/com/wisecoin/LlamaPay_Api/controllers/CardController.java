package com.wisecoin.LlamaPay_Api.controllers;

import com.wisecoin.LlamaPay_Api.dtos.CardDTO;
import com.wisecoin.LlamaPay_Api.dtos.request.CardRequestDTO;
import com.wisecoin.LlamaPay_Api.entities.Card;
import com.wisecoin.LlamaPay_Api.services.CardService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    CardService cardService;

    @GetMapping("/cards")
    public ResponseEntity<List<Card>> listAllCards(){
        return new ResponseEntity<List<Card>>(cardService.listAll(),
                HttpStatus.OK);
    }

    @GetMapping("/card/user/{id}")
    public ResponseEntity<Card> listCardByClient( @PathVariable("id") Long id){
        return new ResponseEntity<Card>(cardService.findByClient(id),
                HttpStatus.OK);
    }

    @PostMapping("/cards/{id}")
    public ResponseEntity<Card> addCard(@PathVariable("id") Long id, @Valid @RequestBody CardDTO cardDto){
        Card carCreated = cardService.addCard(id, cardDto);
        return new ResponseEntity<Card>(carCreated,
                HttpStatus.OK);
    }

    @PutMapping("/cards/{id}")
    public ResponseEntity<Card> updateCard(@PathVariable("id") Long id, @Valid @RequestBody CardRequestDTO cardRequestDto){
        Card cardUpdate = cardService.updateCard(id,cardRequestDto);
        if (cardUpdate!=null) {
            return new ResponseEntity<>(cardUpdate, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @DeleteMapping("/cards/{id}")
    public ResponseEntity<HttpStatus> deleteCard(@PathVariable("id") Long id){
        cardService.deleteCard(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
