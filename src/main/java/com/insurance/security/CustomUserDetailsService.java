package com.insurance.security;

import com.insurance.entity.Customer;
import com.insurance.repository.CustomerRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final CustomerRepository customerRepository;

    public CustomUserDetailsService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException(("User not found with email:"+email)));

        List<SimpleGrantedAuthority> authorities =
                List.of((new SimpleGrantedAuthority((customer.getRole())))); //e.g. ROLE_CUSTOMER

        return new User(customer.getEmail(), customer.getPassword(), authorities);
    }
}
