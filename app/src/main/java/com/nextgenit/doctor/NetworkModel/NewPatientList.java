package com.nextgenit.doctor.NetworkModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class NewPatientList implements Parcelable {
    @SerializedName("appointment_id")
    public int  appointment_id;
    @SerializedName("appoint_date")
    public String  appoint_date;
    @SerializedName("slot_sl")
    public int  slot_sl;
    @SerializedName("patient_id")
    public int  patient_id;
    @SerializedName("patient_name")
    public String  patient_name;
    @SerializedName("age")
    public int  age;
    @SerializedName("gender_txt")
    public String  gender_txt;
    @SerializedName("mobile1")
    public String  mobile1;
    @SerializedName("initial_cc")
    public String  initial_cc;
    @SerializedName("initial_height")
    public String  initial_height;
    @SerializedName("initial_weight")
    public String  initial_weight;
    @SerializedName("pharmacy_id")
    public int  pharmacy_id;
    @SerializedName("pharmacy_name")
    public String  pharmacy_name;

    protected NewPatientList(Parcel in) {
        appointment_id = in.readInt();
        appoint_date = in.readString();
        slot_sl = in.readInt();
        patient_id = in.readInt();
        patient_name = in.readString();
        age = in.readInt();
        gender_txt = in.readString();
        mobile1 = in.readString();
        initial_cc = in.readString();
        initial_height = in.readString();
        initial_weight = in.readString();
        pharmacy_id = in.readInt();
        pharmacy_name = in.readString();
    }

    public static final Creator<NewPatientList> CREATOR = new Creator<NewPatientList>() {
        @Override
        public NewPatientList createFromParcel(Parcel in) {
            return new NewPatientList(in);
        }

        @Override
        public NewPatientList[] newArray(int size) {
            return new NewPatientList[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(appointment_id);
        dest.writeString(appoint_date);
        dest.writeInt(slot_sl);
        dest.writeInt(patient_id);
        dest.writeString(patient_name);
        dest.writeInt(age);
        dest.writeString(gender_txt);
        dest.writeString(mobile1);
        dest.writeString(initial_cc);
        dest.writeString(initial_height);
        dest.writeString(initial_weight);
        dest.writeInt(pharmacy_id);
        dest.writeString(pharmacy_name);
    }
}
