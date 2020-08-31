package com.nextgenit.doctor.NetworkModel;

import com.google.gson.annotations.SerializedName;

public class PrescriptionView {
    @SerializedName("doctor_name")
    public String  doctor_name;
    @SerializedName("degree1")
    public String  degree1;
    @SerializedName("degree2")
    public String  degree2;
    @SerializedName("degree3")
    public String  degree3;
    @SerializedName("degree4")
    public String  degree4;
    @SerializedName("prescription_code")
    public String  prescription_code;
    @SerializedName("prescription_no_pk")
    public int  prescription_no_pk;
    @SerializedName("patient_name")
    public String  patient_name;
    @SerializedName("gender_txt")
    public String  gender_txt;
    @SerializedName("mobile1")
    public String  mobile1;
    @SerializedName("age")
    public String  age;
    @SerializedName("initial_height")
    public String  initial_height;
    @SerializedName("initial_weight")
    public String  initial_weight;
}
