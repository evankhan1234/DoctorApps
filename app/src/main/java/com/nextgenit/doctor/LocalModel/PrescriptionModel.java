package com.nextgenit.doctor.LocalModel;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PrescriptionModel {
    @SerializedName("appointment_id")
    public int  appointment_id;
    @SerializedName("pharmacy_id")
    public int  pharmacy_id;
    @SerializedName("prescription_id")
    public int  prescription_id;
    @SerializedName("created_by")
    public int  created_by;
    @SerializedName("prs_state")
    public String  prs_state;

    @SerializedName("details_data")
    public ArrayList<Details> details_data;
    @SerializedName("medicine_data")
    public ArrayList<Medicine> medicine_data;
    public static class Details {
        @SerializedName("id")
        public int  id;
        @SerializedName("lookup_name")
        public String  lookup_name;
        @SerializedName("lookup_type")
        public String  lookup_type;
        @SerializedName("lookup_id")
        public int  lookup_id;
        @SerializedName("pharmacy_id")
        public int  pharmacy_id;
        @SerializedName("sl")
        public int  sl;
    }
    public static class Medicine {
        @SerializedName("id")
        public int  id;
        @SerializedName("med_name")
        public String  med_name;
        @SerializedName("med_type")
        public String  med_type;
        @SerializedName("med_dose")
        public String  med_dose;
        @SerializedName("med_duration")
        public String  med_duration;
        @SerializedName("med_duration_mu")
        public String  med_duration_mu;
        @SerializedName("med_qnty")
        public int  med_qnty;
        @SerializedName("med_instruction")
        public String  med_instruction;
        @SerializedName("sl")
        public int  sl;
    }
}
