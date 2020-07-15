package com.nextgenit.doctor.NetworkModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MedicationListResponses extends APIResponses {
    @SerializedName("data_list")
    public ArrayList<Medication> data_list;
}
