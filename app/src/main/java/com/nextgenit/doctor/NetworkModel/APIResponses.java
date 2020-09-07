package com.nextgenit.doctor.NetworkModel;

import com.google.gson.annotations.SerializedName;

public class APIResponses {
    @SerializedName("status")
    public String  status;
    @SerializedName("message")
    public String  message;
    @SerializedName("data_session")
    public String  data_session;
    @SerializedName("data_token")
    public String  data_token;
}
