package com.example.inclass09;

import android.graphics.drawable.Drawable;
import android.net.Uri;

import java.io.Serializable;

public class Contact implements Serializable {
    String fullName;
    String email;
    String phone;
    String urlImage;

    public Contact() {
    }

    public Contact(String fullName, String email, String phone, String urlImage) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.urlImage = urlImage;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "fullName='" + fullName + '\'' +
                ", email='" + email + '\'' +
                ", phone=" + phone +
                '}';
    }
}
