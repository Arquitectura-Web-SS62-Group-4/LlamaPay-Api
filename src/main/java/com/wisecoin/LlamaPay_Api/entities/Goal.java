package com.wisecoin.LlamaPay_Api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "goals")
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 40)
    private String name;

    @Column(name = "description", nullable = false, length = 250)
    private String description;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "deadline", nullable = false)
    private LocalDate deadline;

    @Column(name = "is_successfull", nullable = false)
    private Boolean isSuccessfull;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

}
