package com.example.userservice.user.domain;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
@Entity
public abstract class Manager {
    @Id
    @Column(unique = true)
    private String id;

    @Column(nullable = false)
    @NotNull
    private String pw;

    @Embedded
    UserInfo userInfo;
}
