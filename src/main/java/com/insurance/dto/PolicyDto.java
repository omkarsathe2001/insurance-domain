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
}
