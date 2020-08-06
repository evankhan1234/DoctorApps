package com.nextgenit.doctor.NetworkModel;

import com.google.gson.annotations.SerializedName;

public class Diagnosis {
    @SerializedName("lookup_data_id")
    public int  lookup_data_id;
    @SerializedName("lookup_data_code")
    public String  lookup_data_code;
    @SerializedName("lookup_data_name")
    public String  lookup_data_name;
    @SerializedName("lookup_code")
    public String  lookup_code;
    @SerializedName("lookup_id")
    public int  lookup_id;
}
