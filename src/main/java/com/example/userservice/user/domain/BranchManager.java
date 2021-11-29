package com.example.userservice.user.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BranchManager extends Manager{
    @Column(nullable = false)
    private int storeId;

    @Column(nullable = false)
    private boolean approval;

    public static BranchManager createBranchManager(String id,String pw,UserInfo userInfo,int storeId) {
        BranchManager branchManager=new BranchManager();
        branchManager.id=id;
        branchManager.pw=pw;
        branchManager.userInfo=userInfo;
        branchManager.approval=false;
        branchManager.storeId=storeId;
        return branchManager;
    }

    //가입 승인
    public void approvalManager(){
        this.approval=true;
    }




}
