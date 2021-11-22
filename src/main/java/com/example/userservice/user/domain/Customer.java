package com.example.userservice.user.domain;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer {
    @Id
    @Column(unique = true)
    private String id;

    @Column(nullable = false)
    private String pw;

    @Embedded
    UserInfo userInfo;
}
