package com.insurance.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
}
