package com.wisecoin.LlamaPay_Api.controllers;

import com.wisecoin.LlamaPay_Api.dtos.request.UserLoginDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.ClientResponseDTO;
import com.wisecoin.LlamaPay_Api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserService userService;

    @PutMapping("/user/delete/{id}")
    public ResponseEntity<HttpStatus> deleteLogicalUser(@PathVariable("id") Long id){
        userService.logicalDeleteUser(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/user/login")
    public ResponseEntity<ClientResponseDTO> getClientResponseDTOByUsernameAndPassword(@Valid @RequestBody UserLoginDTO userLoginDto){
        return new ResponseEntity<ClientResponseDTO>(userService.getClientResponseByUsernameAndPassword(userLoginDto),
                HttpStatus.OK);
    }
}