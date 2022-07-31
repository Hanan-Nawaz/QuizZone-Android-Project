package com.example.quizzone.Classes;

import com.google.firebase.firestore.PropertyName;

public class AddTopics {

    private String Image, Name;

    public AddTopics(String image, String name) {
        Image = image;
        Name = name;
    }

    @PropertyName("Image")
    public String getImage() {
        return Image;
    }

    @PropertyName("Image")
    public void setImage(String image) {
        Image = image;
    }

    @PropertyName("Name")
    public String getName() {
        return Name;
    }

    @PropertyName("Name")
    public void setName(String name) {
        Name = name;
    }
}
