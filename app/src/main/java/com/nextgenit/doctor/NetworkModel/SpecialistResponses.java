package com.nextgenit.doctor.NetworkModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class SpecialistResponses extends APIResponses {
    @SerializedName("data_list")
    public ArrayList<Specialist> data_list;
}
