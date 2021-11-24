package com.example.userservice.user.repository;

import com.example.userservice.user.domain.Customer;
import com.example.userservice.user.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager,String> {
    Optional<Manager> findManagerById(String id);
}
