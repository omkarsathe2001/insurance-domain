package com.insurance.service;

import com.insurance.dto.CustomerDto;
import java.util.List;

public interface CustomerService {
    CustomerDto register(CustomerDto dto, String rawPassword);
    String login(String email, String password);
    CustomerDto getById(Long id);
    List<CustomerDto> getAll();
    void deleteById(Long id);
    CustomerDto getByEmail(String email);
    CustomerDto updateCustomer(Long id, CustomerDto dto);
}
