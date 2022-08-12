package com.example.quizzone.Classes;

import com.google.firebase.firestore.PropertyName;

public class AddTopics {

     String image, name, adderName, niche, email;

    public AddTopics() {
    }

    public AddTopics(String image, String name, String adderName, String niche, String email) {
        this.image = image;
        this.name = name;
        this.adderName = adderName;
        this.niche = niche;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAdderName() {
        return adderName;
    }

    public void setAdderName(String adderName) {
        this.adderName = adderName;
    }

    public String getNiche() {
        return niche;
    }

    public void setNiche(String niche) {
        this.niche = niche;
    }
}
