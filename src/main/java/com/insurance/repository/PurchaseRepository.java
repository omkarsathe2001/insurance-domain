package com.insurance.repository;

import com.insurance.entity.PolicyPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PurchaseRepository extends JpaRepository<PolicyPurchase, Long> {
    List<PolicyPurchase> findByCustomerId(Long customerId);
}
