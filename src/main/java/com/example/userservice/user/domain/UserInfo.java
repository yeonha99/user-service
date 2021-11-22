package com.example.userservice.user.domain;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;

@Embeddable
public class UserInfo {
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate birthday;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Column(unique = true, nullable = false)
    private String phone_num;
}
