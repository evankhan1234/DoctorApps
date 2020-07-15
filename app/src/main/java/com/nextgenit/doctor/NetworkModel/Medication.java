package com.nextgenit.doctor.NetworkModel;

import com.google.gson.annotations.SerializedName;

public class Medication {
    @SerializedName("item_type")
    public String  item_type;
    @SerializedName("item_id")
    public String  item_id;
    @SerializedName("item_name")
    public String  item_name;
    @SerializedName("item_rate")
    public String  item_rate;
    @SerializedName("item_code")
    public String  item_code;
}
