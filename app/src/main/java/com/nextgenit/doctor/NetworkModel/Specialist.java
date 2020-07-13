package com.nextgenit.doctor.NetworkModel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Specialist implements Parcelable {
    @SerializedName("specialization_id")
    public int  specialization_id;
    @SerializedName("specialization_name")
    public String  specialization_name;
    @SerializedName("no_of_doctor")
    public int  no_of_doctor;

    @Override
    public String toString() {
        return specialization_name;
    }

    protected Specialist(Parcel in) {
        specialization_id = in.readInt();
        specialization_name = in.readString();
        no_of_doctor = in.readInt();
    }

    public static final Creator<Specialist> CREATOR = new Creator<Specialist>() {
        @Override
        public Specialist createFromParcel(Parcel in) {
            return new Specialist(in);
        }

        @Override
        public Specialist[] newArray(int size) {
            return new Specialist[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(specialization_id);
        dest.writeString(specialization_name);
        dest.writeInt(no_of_doctor);
    }
}
