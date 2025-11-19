package com.insurance.service;

import com.insurance.dto.ClaimDto;

import java.util.List;

public interface ClaimService {
    ClaimDto raiseClaim(ClaimDto dto);
    ClaimDto getById(Long id);
    List<ClaimDto> getByPurchaseId(Long purchaseId);
    ClaimDto updateStatus(Long id, String status);
}
