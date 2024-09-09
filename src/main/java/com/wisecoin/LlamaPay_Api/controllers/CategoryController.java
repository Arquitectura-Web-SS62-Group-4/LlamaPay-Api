package com.wisecoin.LlamaPay_Api.controllers;

import com.wisecoin.LlamaPay_Api.dtos.response.CategoryResponseDTO;
import com.wisecoin.LlamaPay_Api.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponseDTO>> listAllCategories(){
        return new ResponseEntity<List<CategoryResponseDTO>>(categoryService.listAll(),
                HttpStatus.OK);
    }


}
