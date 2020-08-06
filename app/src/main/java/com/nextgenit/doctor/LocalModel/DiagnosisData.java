package com.nextgenit.doctor.LocalModel;

import com.google.gson.annotations.SerializedName;

public class DiagnosisData {
    @SerializedName("id")
    public int  id;
    @SerializedName("lookup_name")
    public String  lookup_name;
    @SerializedName("lookup_type")
    public String  lookup_type;
    @SerializedName("lookup_id")
    public int  lookup_id;
    @SerializedName("pharmacy_id")
    public int  pharmacy_id;
    @SerializedName("sl")
    public int  sl;
}
