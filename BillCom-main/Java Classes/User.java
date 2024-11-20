package com.secondapplication.app;

import java.time.LocalDate;
import java.time.Period;

public class User {
    private String userName;
    private String password;
    private int birthYear;
    private int birthMonth;
    private int birthDate;

    public User(String userName, String password, int birthYear, int birthMonth, int birthDate) {
        this.userName = userName;
        this.password = password;
        this.birthYear = birthYear;
        this.birthMonth = birthMonth;
        this.birthDate = birthDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public int getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(int birthMonth) {
        this.birthMonth = birthMonth;
    }

    public int getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(int birthDate) {
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", birthYear=" + birthYear +
                ", birthMonth=" + birthMonth +
                ", birthDate=" + birthDate +
                '}';
    }

    public int getAge(){
        return Period.between(
                LocalDate.of(this.birthYear, this.birthMonth, this.birthDate),
                LocalDate.now()
        ).getYears();
    }
}

