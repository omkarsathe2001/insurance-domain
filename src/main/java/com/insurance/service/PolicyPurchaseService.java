package com.insurance.service;

import com.insurance.dto.PolicyPurchaseDto;

import java.util.List;

public interface PolicyPurchaseService {
    PolicyPurchaseDto purchasePolicy(PolicyPurchaseDto dto);
    PolicyPurchaseDto getById(Long id);
    List<PolicyPurchaseDto> getByCustomerId(Long customerId);
}
