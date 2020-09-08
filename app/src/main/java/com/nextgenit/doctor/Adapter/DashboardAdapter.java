package com.nextgenit.doctor.Adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nextgenit.doctor.Activity.DashboardActivity;
import com.nextgenit.doctor.Activity.PrescriptionEngineActivity;
import com.nextgenit.doctor.Activity.PrescriptionViewAgainActivity;
import com.nextgenit.doctor.Interface.IClickListener;
import com.nextgenit.doctor.Interface.IPharmacyClickListener;
import com.nextgenit.doctor.NetworkModel.NewPatientList;
import com.nextgenit.doctor.NetworkModel.PatientList;
import com.nextgenit.doctor.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardListiewHolder> {


    private Activity mActivity = null;
    private ArrayList<NewPatientList> messageEntities;
    private IClickListener iClickListener;
    private IPharmacyClickListener pharmacyClickListener;
    public DashboardAdapter(Activity activity, ArrayList<NewPatientList> messageEntitie,IClickListener iClickListeners,IPharmacyClickListener pharmacyClickListeners) {
        mActivity = activity;
        messageEntities = messageEntitie;
        iClickListener=iClickListeners;
        pharmacyClickListener=pharmacyClickListeners;
    }


    @Override
    public DashboardAdapter.DashboardListiewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_dashboard_list, null);
        return new DashboardAdapter.DashboardListiewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DashboardAdapter.DashboardListiewHolder holder, final int position) {

        if (messageEntities.get(position).prescription_no_pk!=null){
           holder.linear.setBackgroundColor(mActivity.getResources().getColor(R.color.two));
            holder.tv_date.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
            holder.tv_serial.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
            holder.tv_pharmacy_name.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
            holder.tv_name.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
            holder.tv_phone_number.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
            holder.tv_age.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
            holder.tv_disease.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
            holder.tv_weight.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
            holder.tv_height.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
            holder.image.setVisibility(View.GONE);
        }
        else{
            holder.linear.setBackgroundColor(mActivity.getResources().getColor(R.color.one));

            holder.image.setVisibility(View.VISIBLE);
            holder.tv_date.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
            holder.tv_serial.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
            holder.tv_pharmacy_name.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
            holder.tv_name.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
            holder.tv_phone_number.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
            holder.tv_age.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
            holder.tv_disease.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
            holder.tv_weight.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
            holder.tv_height.setTextColor(mActivity.getResources().getColor(R.color.colorPrimary));
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date(System.currentTimeMillis());
        String currentDate = formatter.format(date);
        holder.tv_date.setText(currentDate);
        holder.tv_serial.setText("Serial no: "+messageEntities.get(position).slot_sl);
        holder.et_email.setText(messageEntities.get(position).initial_cc);
        holder.tv_pharmacy_name.setText(messageEntities.get(position).pharmacy_name);
        holder.tv_name.setText(messageEntities.get(position).patient_name);
        holder.tv_age.setText("Age - "+messageEntities.get(position).age+","+messageEntities.get(position).gender_txt);

        holder.tv_phone_number.setText(messageEntities.get(position).mobile1);
        holder.et_weight.setText(messageEntities.get(position).initial_weight);
        holder.et_height.setText(messageEntities.get(position).initial_height);
        if (messageEntities.get(position).gender_txt.equals("Male")){
            holder.user_icon.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.male));
        }
        else{
            holder.user_icon.setImageDrawable(mActivity.getResources().getDrawable(R.drawable.female));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iClickListener.show(messageEntities.get(position));
            }
        });
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pharmacyClickListener.onClick(messageEntities.get(position).pharmacy_id);
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e("evan", "sd" + messageEntities.size());
        return messageEntities.size();
    }

    public class DashboardListiewHolder extends RecyclerView.ViewHolder {

        private TextView tv_pharmacy_name;
        private TextView tv_weight;
        private TextView tv_height;
        private TextView tv_disease;
        private TextView tv_serial;
        private TextView tv_date;
        private TextView tv_name;
        private TextView tv_age;
        private TextView tv_phone_number;
        private TextView et_weight;
        private TextView et_height;
        private RoundedImageView user_icon;
        private ImageView image;
        private EditText et_email;
        private LinearLayout linear;


        public DashboardListiewHolder(View itemView) {
            super(itemView);

            user_icon = itemView.findViewById(R.id.user_icon);
           tv_pharmacy_name = itemView.findViewById(R.id.tv_pharmacy_name);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_age = itemView.findViewById(R.id.tv_age);
            tv_phone_number = itemView.findViewById(R.id.tv_phone_number);
            et_weight = itemView.findViewById(R.id.et_weight);
            et_height = itemView.findViewById(R.id.et_height);
            image = itemView.findViewById(R.id.image);
            tv_date = itemView.findViewById(R.id.tv_date);
            et_email = itemView.findViewById(R.id.et_email);
            tv_serial = itemView.findViewById(R.id.tv_serial);
            linear = itemView.findViewById(R.id.linear);
            tv_disease = itemView.findViewById(R.id.tv_disease);
            tv_weight = itemView.findViewById(R.id.tv_weight);
            tv_height = itemView.findViewById(R.id.tv_height);


        }
    }
}