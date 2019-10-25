package com.comp90018.photostyle.helpers;

import com.google.gson.annotations.SerializedName;






public class UserList {


    @SerializedName("name")
    public String name;
    @SerializedName("email")
    public String email;
    @SerializedName("success")
    public int success;



    public UserList(String name, String email,int success) {
        this.name = name;
        this.email = email;
        this.success=success;
    }

    public String getName(){
        return this.name;
    }

    public String getEmail(){
        return this.email;
    }
    public int getSuccess(){
        return this.success;
    }

}
