
package com.insurance.service;

import com.insurance.dto.PaymentDto;

import java.util.List;

public interface PaymentService {
    PaymentDto makePayment(PaymentDto dto);
    PaymentDto getById(Long id);
    List<PaymentDto> getByPurchaseId(Long purchaseId);
}
