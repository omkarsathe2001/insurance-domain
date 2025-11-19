package com.insurance.service.impl;

import com.insurance.dto.PolicyPurchaseDto;
import com.insurance.entity.Customer;
import com.insurance.entity.Policy;
import com.insurance.entity.PolicyPurchase;
import com.insurance.exception.ResourceNotFoundException;
import com.insurance.repository.CustomerRepository;
import com.insurance.repository.PolicyRepository;
import com.insurance.repository.PurchaseRepository;
import com.insurance.service.PolicyPurchaseService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PolicyPurchaseServiceImpl implements PolicyPurchaseService {

    private final PurchaseRepository purchaseRepository;
    private final CustomerRepository customerRepository;
    private final PolicyRepository policyRepository;

    public PolicyPurchaseServiceImpl(PurchaseRepository purchaseRepository,
                                     CustomerRepository customerRepository,
                                     PolicyRepository policyRepository) {
        this.purchaseRepository = purchaseRepository;
        this.customerRepository = customerRepository;
        this.policyRepository = policyRepository;
    }

    @Override
    public PolicyPurchaseDto purchasePolicy(PolicyPurchaseDto dto) {
        Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + dto.getCustomerId()));

        // Only admin can buy for others; customer can buy only for themselves
        Customer current = getCurrentCustomer();
        if (!isAdmin(current) && !current.getId().equals(customer.getId())) {
            throw new AccessDeniedException("You cannot purchase policy for another customer");
        }

        Policy policy = policyRepository.findById(dto.getPolicyId())
                .orElseThrow(() -> new ResourceNotFoundException("Policy not found with id: " + dto.getPolicyId()));

        PolicyPurchase purchase = PolicyPurchase.builder()
                .customer(customer)
                .policy(policy)
                .purchaseDate(LocalDate.parse(dto.getPurchaseDate()))
                .expiryDate(LocalDate.parse(dto.getExpiryDate()))
                .status(dto.getStatus() == null ? "ACTIVE" : dto.getStatus())
                .build();

        PolicyPurchase saved = purchaseRepository.save(purchase);
        return mapToDto(saved);
    }

    @Override
    public PolicyPurchaseDto getById(Long id) {
        PolicyPurchase purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found with id: " + id));

        Customer current = getCurrentCustomer();
        if (!isAdmin(current) && !purchase.getCustomer().getId().equals(current.getId())) {
            throw new AccessDeniedException("You are not allowed to view this purchase");
        }

        return mapToDto(purchase);
    }

    @Override
    public List<PolicyPurchaseDto> getByCustomerId(Long customerId) {
        Customer current = getCurrentCustomer();
        if (!isAdmin(current) && !current.getId().equals(customerId)) {
            throw new AccessDeniedException("You are not allowed to view purchases of another customer");
        }

        return purchaseRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private PolicyPurchaseDto mapToDto(PolicyPurchase p) {
        return PolicyPurchaseDto.builder()
                .id(p.getId())
                .customerId(p.getCustomer().getId())
                .policyId(p.getPolicy().getId())
                .purchaseDate(p.getPurchaseDate().toString())
                .expiryDate(p.getExpiryDate().toString())
                .status(p.getStatus())
                .build();
    }

    private Customer getCurrentCustomer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName(); // subject = email
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Logged-in customer not found: " + email));
    }

    private boolean isAdmin(Customer c) {
        return "ROLE_ADMIN".equalsIgnoreCase(c.getRole());
    }
}
