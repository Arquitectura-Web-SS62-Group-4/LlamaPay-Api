package com.wisecoin.LlamaPay_Api.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "card_number", nullable = false, length = 16)
    private String cardNumber;

    @Column(name = "owner_name", nullable = false, length = 80)
    private String ownerName;

    @Column(name = "expiration_date", nullable = false, length = 7)
    private String expirationDate;

    @Column(name = "cvv", nullable = false, length = 3)
    private String cvv;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
