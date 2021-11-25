package com.example.userservice.user.repository;

import com.example.userservice.user.domain.BranchManager;
import com.example.userservice.user.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BranchManagerRepository extends JpaRepository<BranchManager,String> {
    Optional<BranchManager> findBranchManagerById(String id);
    List<BranchManager> findAllByApproval(boolean approval);
}
