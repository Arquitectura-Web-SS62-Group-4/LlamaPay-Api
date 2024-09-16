package com.wisecoin.LlamaPay_Api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_category", nullable = false, length = 40)
    private String nameCategory;

    @Column(name = "type", nullable = false, length = 7)
    private String type; // "Ingreso" o "Gasto"
}
