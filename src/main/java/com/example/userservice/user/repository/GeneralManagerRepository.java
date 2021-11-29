package com.example.userservice.user.repository;

import com.example.userservice.user.domain.GeneralManager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeneralManagerRepository extends JpaRepository<GeneralManager,String> {

}
