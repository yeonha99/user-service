package com.example.userservice.user.repository;

import com.example.userservice.user.domain.BranchManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface BranchManagerRepository extends JpaRepository<BranchManager,String> {
    List<BranchManager> findAllByApproval(boolean approval);
    Optional<BranchManager> findByIdAndApproval(String id,boolean approval);
}
