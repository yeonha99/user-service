package com.example.userservice.user.repository;

import com.example.userservice.user.domain.Customer;
import com.example.userservice.user.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.Optional;
@Repository
public interface ManagerRepository extends JpaRepository<Manager,String> {
}
