//package com.insurance.service.impl;
//
//import com.insurance.dto.CustomerDto;
//import com.insurance.entity.Customer;
//import com.insurance.exception.DuplicateEmailException;
//import com.insurance.exception.ResourceNotFoundException;
//import com.insurance.repository.CustomerRepository;
//import com.insurance.service.CustomerService;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class CustomerServiceImpl implements CustomerService {
//
//    private final CustomerRepository customerRepository;
//
//    public CustomerServiceImpl(CustomerRepository customerRepository) {
//        this.customerRepository = customerRepository;
//    }
//
//    @Override
//    public CustomerDto register(CustomerDto dto, String rawPassword) {
//
//        if (customerRepository.findByEmail(dto.getEmail()).isPresent()) {
//            throw new DuplicateEmailException("Email already registered: " + dto.getEmail());
//        }
//
//        Customer customer = Customer.builder()
//                .name(dto.getName())
//                .email(dto.getEmail())
//                .password(rawPassword)
//                .address(dto.getAddress())
//                .contact(dto.getContact())
//                .role("ROLE_CUSTOMER")
//                .createdDate(LocalDateTime.now())
//                .build();
//
//        Customer saved = customerRepository.save(customer);
//
//        return mapToDto(saved);
//    }
//
//    @Override
//    public String login(String email, String password) {
//        Customer customer = customerRepository.findByEmail(email)
//                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
//
//        if (!customer.getPassword().equals(password)) {
//            throw new RuntimeException("Invalid credentials");
//        }
//
//        return "Login successful for: " + email;
//    }
//
//    @Override
//    public CustomerDto getById(Long id) {
//        Customer customer = customerRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
//        return mapToDto(customer);
//    }
//
//    @Override
//    public List<CustomerDto> getAll() {
//        return customerRepository.findAll()
//                .stream()
//                .map(this::mapToDto)
//                .collect(Collectors.toList());
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        if (!customerRepository.existsById(id)) {
//            throw new ResourceNotFoundException("Customer not found with id: " + id);
//        }
//        customerRepository.deleteById(id);
//    }
//
//    @Override
//    public CustomerDto getByEmail(String email) {
//        Customer customer = customerRepository.findByEmail(email)
//                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with email: " + email));
//        return mapToDto(customer);
//    }
//
//    @Override
//    public CustomerDto updateCustomer(Long id, CustomerDto dto) {
//        Customer customer = customerRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
//
//        customer.setName(dto.getName() != null ? dto.getName() : customer.getName());
//        customer.setAddress(dto.getAddress() != null ? dto.getAddress() : customer.getAddress());
//        customer.setContact(dto.getContact() != null ? dto.getContact() : customer.getContact());
//
//        Customer updated = customerRepository.save(customer);
//
//        return mapToDto(updated);
//    }
//
//    private CustomerDto mapToDto(Customer c) {
//        return CustomerDto.builder()
//                .id(c.getId())
//                .name(c.getName())
//                .email(c.getEmail())
//                .address(c.getAddress())
//                .contact(c.getContact())
//                .build();
//    }
//}




package com.insurance.service.impl;

import com.insurance.dto.CustomerDto;
import com.insurance.entity.Customer;
import com.insurance.exception.DuplicateEmailException;
import com.insurance.exception.ResourceNotFoundException;
import com.insurance.repository.CustomerRepository;
import com.insurance.security.JwtUtil;
import com.insurance.service.CustomerService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public CustomerServiceImpl(CustomerRepository customerRepository,
                               PasswordEncoder passwordEncoder,
                               JwtUtil jwtUtil,
                               AuthenticationManager authenticationManager) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public CustomerDto register(CustomerDto dto, String rawPassword) {

        if (customerRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new DuplicateEmailException("Email already registered: " + dto.getEmail());
        }

        Customer customer = Customer.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .password(passwordEncoder.encode(rawPassword))  // 🔐 ENCODED
                .address(dto.getAddress())
                .contact(dto.getContact())
                .role("ROLE_CUSTOMER")
                .createdDate(LocalDateTime.now())
                .build();

        Customer saved = customerRepository.save(customer);
        return mapToDto(saved);
    }

    @Override
    public String login(String email, String password) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        } catch (BadCredentialsException ex) {
            throw new RuntimeException("Invalid email or password");
        }

        // If authentication successful → generate token
        return jwtUtil.generateToken(email);
    }

    @Override
    public CustomerDto getById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
        return mapToDto(customer);
    }

    @Override
    public List<CustomerDto> getAll() {
        return customerRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
    }

    @Override
    public CustomerDto getByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with email: " + email));
        return mapToDto(customer);
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerDto dto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));

        customer.setName(dto.getName() != null ? dto.getName() : customer.getName());
        customer.setAddress(dto.getAddress() != null ? dto.getAddress() : customer.getAddress());
        customer.setContact(dto.getContact() != null ? dto.getContact() : customer.getContact());

        Customer updated = customerRepository.save(customer);
        return mapToDto(updated);
    }

    private CustomerDto mapToDto(Customer c) {
        return CustomerDto.builder()
                .id(c.getId())
                .name(c.getName())
                .email(c.getEmail())
                .address(c.getAddress())
                .contact(c.getContact())
                .build();
    }
}

