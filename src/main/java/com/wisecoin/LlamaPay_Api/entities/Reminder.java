package com.wisecoin.LlamaPay_Api.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.poi.hpsf.Decimal;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="reminders")
public class Reminder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="client_id" , nullable = false)
    private Client client;

    @Column(name="title",nullable = false,length=30)
    private String title;

    @Column(name="details",nullable=false,length=200)
    private String details;

    @Column(name="amount",nullable=false)
    private Double amount;

    @Column(name="expiration_date",nullable=false)
    private LocalDateTime expiration_date;


}
