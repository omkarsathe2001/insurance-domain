package com.insurance.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
public class PolicyDto {

    private Long id;

    @NotBlank(message = "Policy name is required")
    private String name;

    @NotBlank(message = "Policy type is required")
    private String type;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Premium is required")
    @Min(value = 1, message = "Premium must be greater than 0")
    private Double premium;

    @NotNull(message = "Coverage amount is required")
    @Min(value = 1, message = "Coverage amount must be greater than 0")
    private Double coverageAmount;

    public PolicyDto() {}

    public PolicyDto(Long id, String name, String type, String description, Double premium, Double coverageAmount) {
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
}
