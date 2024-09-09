package com.wisecoin.LlamaPay_Api.services;

import com.wisecoin.LlamaPay_Api.dtos.response.CategoryResponseDTO;
import com.wisecoin.LlamaPay_Api.entities.Category;
import com.wisecoin.LlamaPay_Api.entities.Client;

import java.util.List;

public interface CategoryService {
    public List<CategoryResponseDTO> listAll();
    public Category getCategoryById(Long id);
}
