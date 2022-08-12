package com.example.quizzone.Classes;

import com.google.firebase.firestore.PropertyName;

public class Insert {

    private String email, id, mobileNumber,  password,  name,  occupation,  status, image;

    public Insert(String email, String id, String mobileNumber, String password, String name, String occupation, String status, String image) {
        this.email = email;
        this.id = id;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.name = name;
        this.occupation = occupation;
        this.status = status;
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
