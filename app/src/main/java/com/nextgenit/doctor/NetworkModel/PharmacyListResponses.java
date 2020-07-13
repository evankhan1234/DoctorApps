package com.nextgenit.doctor.NetworkModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PharmacyListResponses extends APIResponses {
    @SerializedName("data_list")
    public ArrayList<Pharmacy> data_list;
}
