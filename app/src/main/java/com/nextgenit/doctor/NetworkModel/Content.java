package com.nextgenit.doctor.NetworkModel;

import com.google.gson.annotations.SerializedName;

public class Content {
    @SerializedName("content")
    public String  content;
    @SerializedName("session_id")
    public String  session_id;
    @SerializedName("session_token")
    public String  session_token;
}
