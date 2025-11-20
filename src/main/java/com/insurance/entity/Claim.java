package com.insurance.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Builder
@Entity
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


    public Claim() {
    }

    public Claim(Long id, PolicyPurchase purchase, Double claimAmount, String reason, String status, LocalDateTime createdDate) {
        this.id = id;
        this.purchase = purchase;
        this.claimAmount = claimAmount;
        this.reason = reason;
        this.status = status;
        this.createdDate = createdDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PolicyPurchase getPurchase() {
        return purchase;
    }

    public void setPurchase(PolicyPurchase purchase) {
        this.purchase = purchase;
    }

    public Double getClaimAmount() {
        return claimAmount;
    }

    public void setClaimAmount(Double claimAmount) {
        this.claimAmount = claimAmount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "Claim{" +
                "id=" + id +
                ", purchase=" + purchase +
                ", claimAmount=" + claimAmount +
                ", reason='" + reason + '\'' +
                ", status='" + status + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}