package com.insurance.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Table(name = "policies")
public class Policy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type;

    @Column(length = 1000)
    private String description;

    private Double premium;
    private Double coverageAmount;


    public Policy() {
    }

    public Policy(Long id, String name, String type, String description, Double premium, Double coverageAmount) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.description = description;
        this.premium = premium;
        this.coverageAmount = coverageAmount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPremium() {
        return premium;
    }

    public void setPremium(Double premium) {
        this.premium = premium;
    }

    public Double getCoverageAmount() {
        return coverageAmount;
    }

    public void setCoverageAmount(Double coverageAmount) {
        this.coverageAmount = coverageAmount;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", premium=" + premium +
                ", coverageAmount=" + coverageAmount +
                '}';
    }
}