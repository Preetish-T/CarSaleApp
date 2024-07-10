package com.example.carsaleapp.Backend;

import java.io.Serializable;
// @author: Preetish Thirumalai u7157098


//made redundant by fi
public class User implements Serializable {
    private String username;
    private String password;

    public String getName() {
        return username;
    }
    public String getWord() {
        return password;
    }

    public void setName(String s) {
        this.username = s;
    }
    public void setWord(String s) {
        this.password = s;
    }

}
