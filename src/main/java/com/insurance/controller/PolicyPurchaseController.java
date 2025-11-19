package com.insurance.controller;

import com.insurance.dto.PolicyPurchaseDto;
import com.insurance.response.ApiResponse;
import com.insurance.service.PolicyPurchaseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchases")
public class PolicyPurchaseController {

    private final PolicyPurchaseService policyPurchaseService;

    public PolicyPurchaseController(PolicyPurchaseService policyPurchaseService) {
        this.policyPurchaseService = policyPurchaseService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PolicyPurchaseDto>> purchasePolicy(@RequestBody @Valid PolicyPurchaseDto dto) {
        PolicyPurchaseDto purchased = policyPurchaseService.purchasePolicy(dto);
        return ResponseEntity.ok(ApiResponse.success(purchased, "Policy purchased successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PolicyPurchaseDto>> getById(@PathVariable Long id) {
        PolicyPurchaseDto dto = policyPurchaseService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(dto, "Purchase fetched successfully"));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<PolicyPurchaseDto>>> getByCustomer(@PathVariable Long customerId) {
        List<PolicyPurchaseDto> list = policyPurchaseService.getByCustomerId(customerId);
        return ResponseEntity.ok(ApiResponse.success(list, "Purchases fetched successfully"));
    }
}
