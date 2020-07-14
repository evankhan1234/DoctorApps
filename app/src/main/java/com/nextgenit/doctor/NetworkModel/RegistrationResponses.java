package com.nextgenit.doctor.NetworkModel;

import com.google.gson.annotations.SerializedName;

public class RegistrationResponses extends APIResponses {
    @SerializedName("user")
    public RegistrationUser user;

}
