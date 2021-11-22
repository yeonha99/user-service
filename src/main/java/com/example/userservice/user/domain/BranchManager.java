package com.example.userservice.user.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
@Getter
@Entity
public class BranchManager extends Manager{
    @Column(nullable = false)
    private int storeId;
    public BranchManager(int store_id) {
        this.storeId = store_id;
    }


}
