package com.example.betterfly;

public class Organization {
    public String name , email , approvalId;
    public Status status;


    public Organization(){

    }


    public Organization(String name, String email, String approvalId, Status status) {
        this.name = name;
        this.email = email;
        this.approvalId = approvalId;
        this.status = status;
    }
}
