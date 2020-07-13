package com.nextgenit.doctor.NetworkModel;

import com.google.gson.annotations.SerializedName;

public class Pharmacy {
    @SerializedName("pharmacy_id")
    public int  pharmacy_id;

    @Override
    public String toString() {
        return pharmacy_name ;

    }

    @SerializedName("pharmacy_name")
    public String  pharmacy_name;
}
