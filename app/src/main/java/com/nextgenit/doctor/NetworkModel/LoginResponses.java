package com.nextgenit.doctor.NetworkModel;

import com.google.gson.annotations.SerializedName;

public class LoginResponses extends APIResponses {

    @SerializedName("access_token")
    public String  access_token;
    @SerializedName("token_type")
    public String  token_type;
    @SerializedName("user")
    public User user;
    @SerializedName("expires_at")
    public String  expires_at;
}
