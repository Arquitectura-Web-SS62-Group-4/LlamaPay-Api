package com.wisecoin.LlamaPay_Api.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "clients")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 20)
    private String lastName;

    @Column(name = "email", nullable = false, length = 30)
    private String email;

    @Column(name = "phone_number", nullable = false, length = 9)
    private String phoneNumber;

    @Column(name = "birthdate", nullable = false)
    private LocalDate birthdate;

    @Column(name = "gender", nullable = false, length = 1)
    private String gender;

    @Column(name = "profile_picture", length = 250)
    private String profilePicture;

    @Column(name = "has_premiun")
    private Boolean hasPremiun;
}
