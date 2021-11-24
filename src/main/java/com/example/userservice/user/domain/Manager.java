package com.example.userservice.user.domain;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
@Getter
@Entity
public abstract class Manager {
    @Id
    @Column(unique = true)
    protected String id;

    @Column(nullable = false)
    protected String pw;

    @Embedded
    protected UserInfo userInfo;

}
