package com.example.contactbookdemo.model;

import java.util.ArrayList;

/**
 * Created by android on 12/10/16.
 */

public class ContactModel {

    private String contactId;
    private String name;
    private String photo;
    private ArrayList<String> phoneNumber;
    private ArrayList<String> emailAddress;
    private ArrayList<String> lastCallDetailList;

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public ArrayList<String> getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(ArrayList<String> phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<String> getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(ArrayList<String> emailAddress) {
        this.emailAddress = emailAddress;
    }

    public ArrayList<String> getLastCallDetailList() {
        return lastCallDetailList;
    }

    public void setLastCallDetailList(ArrayList<String> lastCallDetailList) {
        this.lastCallDetailList = lastCallDetailList;
    }
}
