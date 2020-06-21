package com.example.bloodbank.Models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class RequestDataModel {

    private String id;
    private String message;
    private String url;
    private String number;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

}