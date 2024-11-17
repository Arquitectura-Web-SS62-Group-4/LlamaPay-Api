package com.wisecoin.LlamaPay_Api.serviceImpl;

import com.wisecoin.LlamaPay_Api.dtos.response.CategoryResponseDTO;
import com.wisecoin.LlamaPay_Api.entities.Category;
import com.wisecoin.LlamaPay_Api.exceptions.ResourceNotFoundException;
import com.wisecoin.LlamaPay_Api.repositories.CategoryRepository;
import com.wisecoin.LlamaPay_Api.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponseDTO> listAllByType(String type) {
        List<Category> list = categoryRepository.getListByType(type);
        List<CategoryResponseDTO> categories = new ArrayList<>();
        for(Category category: list){
            CategoryResponseDTO categoryResponseDto = new CategoryResponseDTO(
                    category.getId(), category.getNameCategory()
            );
            categories.add(categoryResponseDto);
        }
        return categories;
    }

    @Override
    public Category getCategoryById(Long id){
        Category category = categoryRepository.findById(id).orElse(null);
        if (category==null) {
            throw new ResourceNotFoundException("Categoria no encontrado");
        }
        return category;
    }
}
