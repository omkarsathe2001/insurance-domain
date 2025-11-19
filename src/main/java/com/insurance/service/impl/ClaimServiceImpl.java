package com.insurance.service.impl;

import com.insurance.dto.ClaimDto;
import com.insurance.entity.Claim;
import com.insurance.entity.Customer;
import com.insurance.entity.PolicyPurchase;
import com.insurance.exception.BadRequestException;
import com.insurance.exception.ResourceNotFoundException;
import com.insurance.repository.ClaimRepository;
import com.insurance.repository.CustomerRepository;
import com.insurance.repository.PurchaseRepository;
import com.insurance.service.ClaimService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClaimServiceImpl implements ClaimService {

    private final ClaimRepository claimRepository;
    private final PurchaseRepository purchaseRepository;
    private final CustomerRepository customerRepository;

    public ClaimServiceImpl(ClaimRepository claimRepository,
                            PurchaseRepository purchaseRepository,
                            CustomerRepository customerRepository) {
        this.claimRepository = claimRepository;
        this.purchaseRepository = purchaseRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public ClaimDto raiseClaim(ClaimDto dto) {
        PolicyPurchase purchase = purchaseRepository.findById(dto.getPurchaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found with id: " + dto.getPurchaseId()));

        Customer current = getCurrentCustomer();
        if (!isAdmin(current) && !purchase.getCustomer().getId().equals(current.getId())) {
            throw new AccessDeniedException("You cannot raise a claim for another customer's purchase");
        }

        Claim claim = Claim.builder()
                .purchase(purchase)
                .claimAmount(dto.getClaimAmount())
                .reason(dto.getReason())
                .status("PENDING")
                .createdDate(LocalDateTime.now())
                .build();

        Claim saved = claimRepository.save(claim);
        return mapToDto(saved);
    }

    @Override
    public ClaimDto getById(Long id) {
        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found with id: " + id));

        Customer current = getCurrentCustomer();
        if (!isAdmin(current) && !claim.getPurchase().getCustomer().getId().equals(current.getId())) {
            throw new AccessDeniedException("You are not allowed to view this claim");
        }

        return mapToDto(claim);
    }

    @Override
    public List<ClaimDto> getByPurchaseId(Long purchaseId) {
        PolicyPurchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found with id: " + purchaseId));

        Customer current = getCurrentCustomer();
        if (!isAdmin(current) && !purchase.getCustomer().getId().equals(current.getId())) {
            throw new AccessDeniedException("You are not allowed to view claims of this purchase");
        }

        return claimRepository.findByPurchaseId(purchaseId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ClaimDto updateStatus(Long id, String status) {
        if (status == null || status.isBlank()) {
            throw new BadRequestException("Status cannot be empty");
        }

        Claim claim = claimRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Claim not found with id: " + id));

        Customer current = getCurrentCustomer();
        // Only admin can change status
        if (!isAdmin(current)) {
            throw new AccessDeniedException("Only admin can update claim status");
        }

        claim.setStatus(status);
        Claim updated = claimRepository.save(claim);
        return mapToDto(updated);
    }

    private ClaimDto mapToDto(Claim c) {
        return ClaimDto.builder()
                .id(c.getId())
                .purchaseId(c.getPurchase().getId())
                .claimAmount(c.getClaimAmount())
                .reason(c.getReason())
                .status(c.getStatus())
                .createdDate(c.getCreatedDate().toString())
                .build();
    }

    private Customer getCurrentCustomer() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Logged-in customer not found: " + email));
    }

    private boolean isAdmin(Customer c) {
        return "ROLE_ADMIN".equalsIgnoreCase(c.getRole());
    }
}
