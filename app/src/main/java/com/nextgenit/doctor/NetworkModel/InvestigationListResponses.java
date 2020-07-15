package com.nextgenit.doctor.NetworkModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class InvestigationListResponses extends APIResponses {
    @SerializedName("data_list")
    public ArrayList<Investigation> data_list;
}
