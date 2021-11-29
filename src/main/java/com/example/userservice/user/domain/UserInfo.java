package com.example.userservice.user.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder
    public UserInfo(String name, LocalDate birthday, Sex sex, String phone_num) {
        this.name = name;
        this.birthday = birthday;
        this.sex = sex;
        this.phone_num = phone_num;
    }

    public void updateInfo(UserInfo userInfo){
        if(userInfo.name!=null)this.name=userInfo.name;
        if(userInfo.phone_num!=null)this.phone_num=userInfo.phone_num;
        if(userInfo.birthday!=null)this.birthday=userInfo.birthday;
        if(userInfo.sex!=null)this.sex=userInfo.sex;
    }
}
