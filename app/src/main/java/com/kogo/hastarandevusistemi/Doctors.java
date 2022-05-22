package com.kogo.hastarandevusistemi;

public class Doctors {
    private String full_name;
    private String user_status;
    private String gender;
    private String urlImage;

    public Doctors() {
    }

    public Doctors(String full_name, String user_status, String gender, String urlImage) {
        this.full_name = full_name;
        this.user_status = user_status;
        this.gender = gender;
        this.urlImage = urlImage;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUser_status() {
        return user_status;
    }

    public void setUser_status(String user_status) {
        this.user_status = user_status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }
}
