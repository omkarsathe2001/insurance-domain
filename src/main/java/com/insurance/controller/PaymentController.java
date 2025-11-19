package com.insurance.controller;

import com.insurance.dto.PaymentDto;
import com.insurance.response.ApiResponse;
import com.insurance.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<PaymentDto>> makePayment(@RequestBody @Valid PaymentDto dto) {
        PaymentDto paid = paymentService.makePayment(dto);
        return ResponseEntity.ok(ApiResponse.success(paid, "Payment completed successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PaymentDto>> getById(@PathVariable Long id) {
        PaymentDto dto = paymentService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(dto, "Payment fetched successfully"));
    }

    @GetMapping("/purchase/{purchaseId}")
    public ResponseEntity<ApiResponse<List<PaymentDto>>> getByPurchase(@PathVariable Long purchaseId) {
        List<PaymentDto> list = paymentService.getByPurchaseId(purchaseId);
        return ResponseEntity.ok(ApiResponse.success(list, "Payments fetched successfully"));
    }
}
