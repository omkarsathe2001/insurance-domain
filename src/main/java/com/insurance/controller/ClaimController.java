package com.insurance.controller;

import com.insurance.dto.ClaimDto;
import com.insurance.response.ApiResponse;
import com.insurance.service.ClaimService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ClaimDto>> raiseClaim(@RequestBody @Valid ClaimDto dto) {
        ClaimDto raised = claimService.raiseClaim(dto);
        return ResponseEntity.ok(ApiResponse.success(raised, "Claim raised successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ClaimDto>> getById(@PathVariable Long id) {
        ClaimDto dto = claimService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(dto, "Claim fetched successfully"));
    }

    @GetMapping("/purchase/{purchaseId}")
    public ResponseEntity<ApiResponse<List<ClaimDto>>> getByPurchase(@PathVariable Long purchaseId) {
        List<ClaimDto> list = claimService.getByPurchaseId(purchaseId);
        return ResponseEntity.ok(ApiResponse.success(list, "Claims fetched successfully"));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ClaimDto>> updateStatus(@PathVariable Long id,
                                                              @RequestParam String status) {
        ClaimDto updated = claimService.updateStatus(id, status);
        return ResponseEntity.ok(ApiResponse.success(updated, "Claim status updated successfully"));
    }
}
