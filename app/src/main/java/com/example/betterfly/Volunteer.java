package com.example.betterfly;

import java.util.Date;

public class Volunteer {
    public String name , email;
    public Date dob;

    public Volunteer(){

    }

    public Volunteer(String name, String email, Date dob) {
        this.name = name;
        this.email = email;
        this.dob = dob;
    }
}
