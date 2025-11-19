package com.insurance.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "claims")
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private PolicyPurchase purchase;


    private Double claimAmount;
    private String reason;
    private String status; // PENDING, APPROVED, REJECTED
    private LocalDateTime createdDate;
}