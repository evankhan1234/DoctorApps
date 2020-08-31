package com.nextgenit.doctor.NetworkModel;

import com.google.gson.annotations.SerializedName;

public class MedList {
    @SerializedName("item_no_fk")
    public int  item_no_fk;
    @SerializedName("brand_name")
    public String  brand_name;
    @SerializedName("dosage")
    public String  dosage;
    @SerializedName("duration")
    public String  duration;
    @SerializedName("duration_mu")
    public String  duration_mu;
    @SerializedName("report_serial")
    public String  report_serial;
    @SerializedName("item_qty")
    public int  item_qty;
    @SerializedName("relation_with_meal")
    public String  relation_with_meal;
}
