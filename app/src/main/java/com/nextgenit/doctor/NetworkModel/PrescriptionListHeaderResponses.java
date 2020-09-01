package com.nextgenit.doctor.NetworkModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PrescriptionListHeaderResponses extends APIResponses {
    @SerializedName("details_data")
    public ArrayList<DetailsList> details_data;
    @SerializedName("med_data")
    public ArrayList<MedList> med_data;
}
