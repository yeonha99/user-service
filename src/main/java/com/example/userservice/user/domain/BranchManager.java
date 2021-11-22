package com.example.userservice.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class BranchManager extends Manager{
    @Column(nullable = false)
    private int store_id;
}
