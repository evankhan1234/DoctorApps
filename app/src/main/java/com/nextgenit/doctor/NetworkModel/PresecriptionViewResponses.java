package com.nextgenit.doctor.NetworkModel;

import com.google.gson.annotations.SerializedName;

public class PresecriptionViewResponses extends APIResponses {
    @SerializedName("data_list")
    public PrescriptionView data_list;
}
