package com.insurance.controller;

import com.insurance.dto.CustomerDto;
import com.insurance.response.ApiResponse;
import com.insurance.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // REGISTER
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<CustomerDto>> register(
            @RequestBody @Valid CustomerDto dto,
            @RequestParam String password) {

        CustomerDto saved = customerService.register(dto, password);
        return ResponseEntity.ok(ApiResponse.success(saved, "Customer registered successfully"));
    }

    // LOGIN
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(@RequestParam String email,
                                                     @RequestParam String password) {

        String message = customerService.login(email, password);
        return ResponseEntity.ok(ApiResponse.success(message, "Login successful"));
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerDto>> getById(@PathVariable Long id) {
        CustomerDto dto = customerService.getById(id);
        return ResponseEntity.ok(ApiResponse.success(dto, "Customer fetched successfully"));
    }

    // GET BY EMAIL
    @GetMapping("/email")
    public ResponseEntity<ApiResponse<CustomerDto>> getByEmail(@RequestParam String email) {
        CustomerDto dto = customerService.getByEmail(email);
        return ResponseEntity.ok(ApiResponse.success(dto, "Customer fetched successfully"));
    }

    // GET ALL
    @GetMapping
    public ResponseEntity<ApiResponse<List<CustomerDto>>> getAll() {
        List<CustomerDto> list = customerService.getAll();
        return ResponseEntity.ok(ApiResponse.success(list, "All customers fetched successfully"));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerDto>> updateCustomer(
            @PathVariable Long id,
            @RequestBody CustomerDto dto) {

        CustomerDto updated = customerService.updateCustomer(id, dto);
        return ResponseEntity.ok(ApiResponse.success(updated, "Customer updated successfully"));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteCustomer(@PathVariable Long id) {
        customerService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success("Deleted", "Customer deleted successfully"));
    }
}
