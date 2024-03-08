package com.example.firstapp.api_response;

import com.example.firstapp.model.UserModel;
import com.google.gson.annotations.SerializedName;

public class UserRespons {
    private boolean Status;

   @SerializedName("User")

    private UserModel userModel;

    public boolean isStatus() {
        return Status;
    }

    public UserModel getUserModel() {
        return userModel;
    }
}
