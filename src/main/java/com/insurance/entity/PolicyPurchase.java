package com.insurance.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;


@Builder
@Entity
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


    public PolicyPurchase() {
    }

    public PolicyPurchase(Long id, Customer customer, Policy policy, LocalDate purchaseDate, LocalDate expiryDate, String status) {
        this.id = id;
        this.customer = customer;
        this.policy = policy;
        this.purchaseDate = purchaseDate;
        this.expiryDate = expiryDate;
        this.status = status;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PolicyPurchase{" +
                "id=" + id +
                ", customer=" + customer +
                ", policy=" + policy +
                ", purchaseDate=" + purchaseDate +
                ", expiryDate=" + expiryDate +
                ", status='" + status + '\'' +
                '}';
    }
}