package com.nextgenit.doctor.NetworkModel;

import com.google.gson.annotations.SerializedName;

public class RegistrationUser {
    @SerializedName("person_no_pk")
    public int  person_no_pk;
    @SerializedName("person_code")
    public String  person_code;
    @SerializedName("email_personal")
    public String  email_personal;
    @SerializedName("au_update_at")
    public String  au_update_at;
    @SerializedName("full_name")
    public String  full_name;
    @SerializedName("au_entry_at")
    public String  au_entry_at;
    @SerializedName("email")
    public String  email;
}
