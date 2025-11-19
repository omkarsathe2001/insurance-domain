package com.insurance.service.impl;

import com.insurance.dto.PaymentDto;
import com.insurance.entity.Customer;
import com.insurance.entity.Payment;
import com.insurance.entity.PolicyPurchase;
import com.insurance.exception.ResourceNotFoundException;
import com.insurance.repository.CustomerRepository;
import com.insurance.repository.PaymentRepository;
import com.insurance.repository.PurchaseRepository;
import com.insurance.service.PaymentService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PurchaseRepository purchaseRepository;
    private final CustomerRepository customerRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository,
                              PurchaseRepository purchaseRepository,
                              CustomerRepository customerRepository) {
        this.paymentRepository = paymentRepository;
        this.purchaseRepository = purchaseRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public PaymentDto makePayment(PaymentDto dto) {
        PolicyPurchase purchase = purchaseRepository.findById(dto.getPurchaseId())
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found with id: " + dto.getPurchaseId()));

        Customer current = getCurrentCustomer();
        if (!isAdmin(current) && !purchase.getCustomer().getId().equals(current.getId())) {
            throw new AccessDeniedException("You cannot pay for another customer's purchase");
        }

        Payment payment = Payment.builder()
                .purchase(purchase)
                .amount(dto.getAmount())
                .paymentDate(LocalDateTime.parse(dto.getPaymentDate()))
                .paymentMode(dto.getPaymentMode())
                .build();

        Payment saved = paymentRepository.save(payment);
        return mapToDto(saved);
    }

    @Override
    public PaymentDto getById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));

        Customer current = getCurrentCustomer();
        if (!isAdmin(current) && !payment.getPurchase().getCustomer().getId().equals(current.getId())) {
            throw new AccessDeniedException("You are not allowed to view this payment");
        }

        return mapToDto(payment);
    }

    @Override
    public List<PaymentDto> getByPurchaseId(Long purchaseId) {
        PolicyPurchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found with id: " + purchaseId));

        Customer current = getCurrentCustomer();
        if (!isAdmin(current) && !purchase.getCustomer().getId().equals(current.getId())) {
            throw new AccessDeniedException("You are not allowed to view payments of this purchase");
        }

        return paymentRepository.findByPurchaseId(purchaseId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private PaymentDto mapToDto(Payment p) {
        return PaymentDto.builder()
                .id(p.getId())
                .purchaseId(p.getPurchase().getId())
                .amount(p.getAmount())
                .paymentDate(p.getPaymentDate().toString())
                .paymentMode(p.getPaymentMode())
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
