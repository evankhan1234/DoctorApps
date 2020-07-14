package com.nextgenit.doctor.NetworkModel;

import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("user_no_pk")
    public int  user_no_pk;
    @SerializedName("user_code")
    public String  user_code;
    @SerializedName("user_fullname")
    public String  user_fullname;
    @SerializedName("email")
    public String  email;
    @SerializedName("user_name")
    public String  user_name;
    @SerializedName("user_expire_date")
    public String  user_expire_date;
    @SerializedName("user_expire_ind")
    public String  user_expire_ind;
    @SerializedName("user_lock_ind")
    public String  user_lock_ind;
    @SerializedName("pass_expiry_ind")
    public String  pass_expiry_ind;
    @SerializedName("default_module_pk_no")
    public String  default_module_pk_no;
    @SerializedName("default_page")
    public String  default_page;
    @SerializedName("person_code")
    public String  person_code;
    @SerializedName("person_no_fk")
    public String  person_no_fk;
    @SerializedName("status")
    public String  status;
    @SerializedName("au_entry_by")
    public String  au_entry_by;
    @SerializedName("au_entry_at")
    public String  au_entry_at;
    @SerializedName("au_entry_session")
    public String  au_entry_session;
    @SerializedName("au_entry_hospital_pk_no")
    public String  au_entry_hospital_pk_no;
    @SerializedName("au_update_at")
    public String  au_update_at;
    @SerializedName("au_update_session")
    public String  au_update_session;
    @SerializedName("au_update_hospital_pk_no")
    public String  au_update_hospital_pk_no;
    @SerializedName("au_sync_hospital_pk_no")
    public String  au_sync_hospital_pk_no;
    @SerializedName("au_sync_at")
    public String  au_sync_at;
    @SerializedName("hospital_no_pk")
    public String  hospital_no_pk;
    @SerializedName("password_expiry_date")
    public String  password_expiry_date;

}
