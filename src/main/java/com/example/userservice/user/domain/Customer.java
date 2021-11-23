package com.example.userservice.user.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer {
    @Id
    @Column(unique = true)
    private String id;

    @Column(nullable = false)
    private String pw;

    @Embedded
    UserInfo userInfo;

    public static Customer createCustomer (String id, String pw, UserInfo userInfo) {
        Customer customer=new Customer();
        customer.userInfo=userInfo;
        customer.id=id;
        customer.pw=pw;
        return customer;
    }

    public void updateCustomer(UserInfo userInfo){
        this.userInfo.updateInfo(userInfo);
    }

    public void updatePw(String pw){
        this.pw=pw;
    }

}
