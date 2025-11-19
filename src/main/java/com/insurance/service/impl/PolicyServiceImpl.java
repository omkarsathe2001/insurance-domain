package com.insurance.service.impl;

import com.insurance.dto.PolicyDto;
import com.insurance.entity.Policy;
import com.insurance.exception.ResourceNotFoundException;
import com.insurance.repository.PolicyRepository;
import com.insurance.service.PolicyService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PolicyServiceImpl implements PolicyService {

    private final PolicyRepository policyRepository;

    public PolicyServiceImpl(PolicyRepository policyRepository) {
        this.policyRepository = policyRepository;
    }

    @Override
    public PolicyDto createPolicy(PolicyDto dto) {
        Policy policy = Policy.builder()
                .name(dto.getName())
                .type(dto.getType())
                .description(dto.getDescription())
                .premium(dto.getPremium())
                .coverageAmount(dto.getCoverageAmount())
                .build();

        Policy saved = policyRepository.save(policy);
        return mapToDto(saved);
    }

    @Override
    public PolicyDto updatePolicy(Long id, PolicyDto dto) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with id: " + id));

        policy.setName(dto.getName());
        policy.setType(dto.getType());
        policy.setDescription(dto.getDescription());
        policy.setPremium(dto.getPremium());
        policy.setCoverageAmount(dto.getCoverageAmount());

        Policy updated = policyRepository.save(policy);
        return mapToDto(updated);
    }

    @Override
    public void deletePolicy(Long id) {
        if (!policyRepository.existsById(id)) {
            throw new ResourceNotFoundException("Policy not found with id: " + id);
        }
        policyRepository.deleteById(id);
    }

    @Override
    public PolicyDto getById(Long id) {
        Policy policy = policyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with id: " + id));
        return mapToDto(policy);
    }

    @Override
    public List<PolicyDto> getAll() {
        return policyRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private PolicyDto mapToDto(Policy p) {
        return PolicyDto.builder()
                .id(p.getId())
                .name(p.getName())
                .type(p.getType())
                .description(p.getDescription())
                .premium(p.getPremium())
                .coverageAmount(p.getCoverageAmount())
                .build();
    }
}
