package com.insurance.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Builder
@Entity
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


    public Payment() {
    }

    public Payment(Long id, PolicyPurchase purchase, Double amount, LocalDateTime paymentDate, String paymentMode) {
        this.id = id;
        this.purchase = purchase;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMode = paymentMode;
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", purchase=" + purchase +
                ", amount=" + amount +
                ", paymentDate=" + paymentDate +
                ", paymentMode='" + paymentMode + '\'' +
                '}';
    }
}