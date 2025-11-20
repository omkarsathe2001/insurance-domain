package com.insurance.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
public class ClaimDto {

    private Long id;

    @NotNull(message = "Purchase id is required")
    private Long purchaseId;

    @NotNull(message = "Claim amount is required")
    @Min(value = 1, message = "Claim amount must be greater than 0")
    private Double claimAmount;

    @NotBlank(message = "Reason is required")
    private String reason;

    private String status;
    private String createdDate;


    public ClaimDto() {
    }

    public ClaimDto(Long id, Long purchaseId, Double claimAmount, String reason, String status, String createdDate) {
        this.id = id;
        this.purchaseId = purchaseId;
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

    public Long getPurchaseId() {
        return purchaseId;
    }

    public void setPurchaseId(Long purchaseId) {
        this.purchaseId = purchaseId;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
