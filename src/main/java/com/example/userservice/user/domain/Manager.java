package com.example.userservice.user.domain;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public abstract class Manager {
    @Id
    @Column(unique = true)
    private String id;

    @Column(nullable = false)
    private String pw;

    @Embedded
    UserInfo userInfo;


}
