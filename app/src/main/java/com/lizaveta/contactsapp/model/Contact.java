package com.lizaveta.contactsapp.model;

public class Contact {
    public String name;
    public String phone;
    public String photoUri;

    public Contact(String name, String phone, String photoUri) {
        this.name = name;
        this.phone = phone;
        this.photoUri = photoUri;
    }
}