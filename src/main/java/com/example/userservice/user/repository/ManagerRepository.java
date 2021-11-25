package com.example.userservice.user.repository;

import com.example.userservice.user.domain.Customer;
import com.example.userservice.user.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ManagerRepository extends JpaRepository<Manager,String> {
    Optional<Manager> findManagerById(String id);
    Optional<Manager> findManagerByIdAndApproval(String id,boolean approval);
}
