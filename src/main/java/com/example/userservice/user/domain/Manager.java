package com.example.userservice.user.domain;
import lombok.Getter;

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

    public void updateManager(UserInfo userInfo){
        this.userInfo.updateInfo(userInfo);
    }

    public void updatePw(String pw){
        this.pw=pw;
    }
}
