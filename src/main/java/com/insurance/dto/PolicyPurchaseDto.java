package com.insurance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
public class PolicyPurchaseDto {

    private Long id;

    @NotNull(message = "Customer id is required")
    private Long customerId;

    @NotNull(message = "Policy id is required")
    private Long policyId;

    @NotBlank(message = "Purchase date is required")
    private String purchaseDate; // "2025-11-17"

    @NotBlank(message = "Expiry date is required")
    private String expiryDate;

    private String status;


    public PolicyPurchaseDto() {}

    public PolicyPurchaseDto(Long id, Long customerId, Long policyId, String purchaseDate, String expiryDate, String status) {
        this.id = id;
        this.customerId = customerId;
        this.policyId = policyId;
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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(Long policyId) {
        this.policyId = policyId;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
