package com.wisecoin.LlamaPay_Api.controllers;

import com.wisecoin.LlamaPay_Api.dtos.response.CategoryResponseDTO;
import com.wisecoin.LlamaPay_Api.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/categories/type/{type}")
    public ResponseEntity<List<CategoryResponseDTO>> listAllCategoriesByType(@PathVariable("type")String type){
        return new ResponseEntity<List<CategoryResponseDTO>>(categoryService.listAllByType(type),
                HttpStatus.OK);
    }


}
