package com.wisecoin.LlamaPay_Api.repositories;

import com.wisecoin.LlamaPay_Api.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query(nativeQuery = true, value = "" +
            "SELECT * FROM categories c " +
            "WHERE c.type = :type ")
    public List<Category> getListByType(@Param("type") String type);
}
