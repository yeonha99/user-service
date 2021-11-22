package com.example.userservice.user.repository;

import com.example.userservice.user.domain.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,String> {
        Optional<Customer> findCustomerById(String id);
        Optional<Customer> findCustomerByIdAndPw(String id,String pw);
}
