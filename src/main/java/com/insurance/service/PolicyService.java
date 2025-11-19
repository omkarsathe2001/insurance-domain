package com.insurance.service;

import com.insurance.dto.PolicyDto;

import java.util.List;

public interface PolicyService {
    PolicyDto createPolicy(PolicyDto dto);
    PolicyDto updatePolicy(Long id, PolicyDto dto);
    void deletePolicy(Long id);
    PolicyDto getById(Long id);
    List<PolicyDto> getAll();
}
