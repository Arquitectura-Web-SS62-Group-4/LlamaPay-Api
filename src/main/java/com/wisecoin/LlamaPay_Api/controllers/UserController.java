package com.wisecoin.LlamaPay_Api.controllers;

import com.wisecoin.LlamaPay_Api.dtos.ClientDTO;
import com.wisecoin.LlamaPay_Api.dtos.TokenDTO;
import com.wisecoin.LlamaPay_Api.dtos.UserClientDTO;
import com.wisecoin.LlamaPay_Api.dtos.UserDTO;
import com.wisecoin.LlamaPay_Api.dtos.response.ClientResponseDTO;
import com.wisecoin.LlamaPay_Api.entities.User;
import com.wisecoin.LlamaPay_Api.security.JwtUtilService;
import com.wisecoin.LlamaPay_Api.security.SecurityUser;
import com.wisecoin.LlamaPay_Api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtilService jwtUtilService;

    @Autowired
    UserService userService;

    @PostMapping("/users/register")
    public ResponseEntity<User> insertUser(@RequestBody UserDTO userDTO){
        User newUser = userService.addUser(userDTO);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/users/clientRegister")
    public ResponseEntity<User> addUserClient(@RequestBody UserClientDTO userClientDTO){
        User newUser = userService.addUserClient(userClientDTO.getUserDTO(),userClientDTO.getClientDTO());
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/users/login")
    public ResponseEntity<TokenDTO> login(@RequestBody UserDTO userDTO) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword()));
        SecurityUser securityUser = new SecurityUser(userService.findByUsername(userDTO.getUsername()));
        String jwt = jwtUtilService.generateToken(securityUser);
        Long user_id = securityUser.getUser().getId();

        String authString = securityUser.getUser().getAuthorities().stream().map(a->a.getName()).collect(Collectors.joining(";"));

        return new ResponseEntity<>(new TokenDTO(jwt,user_id,authString ),HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> listUserById(@PathVariable Long id){
        User userFound = userService.findById(id);
        return new ResponseEntity<>(userFound, HttpStatus.OK);
    }

    @PutMapping("/users/delete/{id}")
    public ResponseEntity<HttpStatus> deleteLogicalUser(@PathVariable("id") Long id){
        userService.logicalDeleteUser(id);
        return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);
    }
}