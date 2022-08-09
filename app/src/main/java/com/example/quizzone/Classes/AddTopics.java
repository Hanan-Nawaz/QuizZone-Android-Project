package com.example.quizzone.Classes;

import com.google.firebase.firestore.PropertyName;

public class AddTopics {

     String image, name;

    public AddTopics() {
    }

    public AddTopics(String image, String name) {
        this.image = image;
        this.name = name;
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
}
