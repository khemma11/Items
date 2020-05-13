package model;

import lombok.Data;

import java.io.Serializable;
@Data
public class User implements Serializable {
    private String name;
    private String surName;
    private transient int age;
    private Gender gender;
    private String phonNumber;
    private String password;


}
