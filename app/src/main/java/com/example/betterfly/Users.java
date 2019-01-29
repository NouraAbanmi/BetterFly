package com.example.betterfly;

public class Users {
    public String name , email , approvalId;
    public Status status;
    public Type type;


    public Users(){

    }

    public Users(String name, String email, String approvalId, Status status, Type type) {
        this.name = name;
        this.email = email;
        this.approvalId = approvalId;
        this.status = status;
        this.type = type;
    }
}
