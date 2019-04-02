package com.example.inclass09;

public class Contact{
    String fullName;
    String email;
    int phone;

    public Contact() {
    }

    public Contact(String fullName, String email, int phone) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public int getPhone() {
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
