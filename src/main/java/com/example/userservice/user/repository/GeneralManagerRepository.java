package com.example.userservice.user.repository;

import com.example.userservice.user.domain.GeneralManager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GeneralManagerRepository extends JpaRepository<GeneralManagerRepository,String> {
    Optional<GeneralManager> findGeneralManagerById(String id);
}
