package com.insurance.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "purchases")
public class PolicyPurchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private Customer customer;


    @ManyToOne
    private Policy policy;


    private LocalDate purchaseDate;
    private LocalDate expiryDate;
    private String status; // ACTIVE, EXPIRED, CANCELLED
}