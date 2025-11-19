package com.insurance.controller;

import com.insurance.dto.PolicyDto;
import com.insurance.response.ApiResponse;
import com.insurance.service.PolicyService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService policyService;

    public PolicyController(PolicyService policyService) {
        this.policyService = policyService;
    }

    // Only ADMIN can create policy
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<ApiResponse<PolicyDto>> createPolicy(@RequestBody @Valid PolicyDto dto) {
        PolicyDto created = policyService.createPolicy(dto);
        return ResponseEntity.ok(ApiResponse.success(created, "Policy created successfully"));
    }

    // Only ADMIN can update policy
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PolicyDto>> updatePolicy(@PathVariable Long id,
                                                               @RequestBody @Valid PolicyDto dto) {
        PolicyDto updated = policyService.updatePolicy(id, dto);
        return ResponseEntity.ok(ApiResponse.success(updated, "Policy updated successfully"));
    }

    // Only ADMIN can delete policy
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePolicy(@PathVariable Long id) {
        policyService.deletePolicy(id);
        return ResponseEntity.ok(ApiResponse.success("Deleted", "Policy deleted successfully"));
    }

    // Any authenticated user (ADMIN or CUSTOMER) can view by id
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PolicyDto>> getPolicy(@PathVariable Long id) {
        PolicyDto dto = policyService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(dto, "Policy fetched successfully"));
    }

    // Any authenticated user (ADMIN or CUSTOMER) can view all
    @GetMapping
    public ResponseEntity<ApiResponse<List<PolicyDto>>> getAllPolicies() {
        List<PolicyDto> list = policyService.getAll();
        return ResponseEntity.ok(ApiResponse.success(list, "All policies fetched successfully"));
    }
}
