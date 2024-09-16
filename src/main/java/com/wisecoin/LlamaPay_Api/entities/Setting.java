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
@Table(name="settings")
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "theme", nullable = false, length = 1)
    private String theme;

    @Column(name = "size", nullable = false, length = 1)
    private String size;

    @Column(name = "language", nullable = false, length = 1)
    private String language;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;
}
