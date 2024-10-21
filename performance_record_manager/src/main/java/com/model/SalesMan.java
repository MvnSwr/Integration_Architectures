package com.model;

// Source: https://github.com/aldaGit/mongodbstart/blob/master/src/main/java/de/hbrs/ia/model/SalesMan.java
// access: 20.10.2024

import org.bson.Document;

// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.Setter;

// @Getter
// @Setter
// @AllArgsConstructor
public class SalesMan {
    private String firstName;
    private String lastName;
    private Integer sid;

    ///
    public SalesMan(String firstname, String lastname, Integer sid) {
        this.firstName = firstname;
        this.lastName = lastname;
        this.sid = sid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstname) {
        this.firstName = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        this.lastName = lastname;
    }

    public Integer getSid() {
        return sid;
    }

    public void setSid(Integer sid) {
        this.sid = sid;
    }
    ///

    public Document toDocument() {
        org.bson.Document document = new Document();
        document.append("firstname" , this.firstName );
        document.append("lastname" , this.lastName );
        document.append("sid" , this.sid);
        return document;
    }

    public String toString() {
        String s = "\nFirstname: " + firstName + "\nLastname: " + lastName + "\nid: " + sid;
        return s;
    }
}