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
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private PolicyPurchase purchase;


    private Double amount;
    private LocalDateTime paymentDate;
    private String paymentMode; // UPI, CARD, NETBANKING
}