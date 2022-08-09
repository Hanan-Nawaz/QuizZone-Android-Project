package com.example.quizzone.Classes;

import com.google.firebase.firestore.PropertyName;

public class Insert {

    private String Email, Id, MobileNumber,  Password,  Name,  Occupation,  Status;

    public Insert(String Email, String Id, String MobileNumber, String Password, String Name, String Occupation, String Status) {
        this.Email = Email;
        this.Id = Id;
        this.MobileNumber = MobileNumber;
        this.Password = Password;
        this.Name = Name;
        this.Occupation = Occupation;
        this.Status = Status;
    }

    @PropertyName("Email")
    public String getEmail() {
        return Email;
    }

    @PropertyName("Email")
    public void setEmail(String email) {
        Email = email;
    }

    @PropertyName("ID")
    public String getId() {
        return Id;
    }

    @PropertyName("ID")
    public void setId(String id) {
        Id = id;
    }

    @PropertyName("MobileNumber")
    public String getMobileNumber() {
        return MobileNumber;
    }

    @PropertyName("MobileNumber")
    public void setMobileNumber(String mobileNumber) {
        MobileNumber = mobileNumber;
    }

    @PropertyName("Password")
    public String getPassword() {
        return Password;
    }

    @PropertyName("Password")
    public void setPassword(String password) {
        Password = password;
    }

    @PropertyName("Name")
    public String getName() {
        return Name;
    }

    @PropertyName("Name")
    public void setName(String name) {
        Name = name;
    }

    @PropertyName("Occupation")
    public String getOccupation() {
        return Occupation;
    }

    @PropertyName("Occupation")
    public void setOccupation(String occupation) {
        Occupation = occupation;
    }

    @PropertyName("Status")
    public String getStatus() {
        return Status;
    }

    @PropertyName("Status")
    public void setStatus(String status) {
        Status = status;
    }
}
