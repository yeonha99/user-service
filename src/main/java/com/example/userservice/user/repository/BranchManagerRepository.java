package com.example.userservice.user.repository;

import com.example.userservice.user.domain.BranchManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface BranchManagerRepository extends JpaRepository<BranchManager,String> {
    Page<BranchManager> findAllByApproval(boolean approval, Pageable pageable);
    Optional<BranchManager> findByIdAndApproval(String id,boolean approval);
}
