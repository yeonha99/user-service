package com.example.userservice.user.repository;

import com.example.userservice.user.domain.BranchManager;
import com.example.userservice.user.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchManagerRepository extends JpaRepository<BranchManager,String> {
    Optional<BranchManager> findBranchManagerById(String id);
}
