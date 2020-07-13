package com.nextgenit.doctor.NetworkModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PatientListResponses extends APIResponses {
    @SerializedName("data_list")
    public ArrayList<PatientList> data_list;
}
