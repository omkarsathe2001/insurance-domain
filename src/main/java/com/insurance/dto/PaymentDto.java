package com.insurance.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {

    private Long id;

    @NotNull(message = "Purchase id is required")
    private Long purchaseId;

    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Amount must be greater than 0")
    private Double amount;

    @NotBlank(message = "Payment date is required")
    private String paymentDate; // "2025-11-17T10:20:30"

    @NotBlank(message = "Payment mode is required")
    private String paymentMode; // UPI, CARD, NETBANKING
}
