package com.nextgenit.doctor.NetworkModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DiagnosisListReponses extends APIResponses {
    @SerializedName("data_list")
    public ArrayList<Diagnosis> data_list;
}
