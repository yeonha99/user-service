package com.example.userservice.user.repository;

import com.example.userservice.user.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ManagerRepository extends JpaRepository<Manager,String> {
}
