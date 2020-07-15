package com.nextgenit.doctor.Adapter;

import android.app.Activity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;
import com.nextgenit.doctor.Interface.IClickListener;
import com.nextgenit.doctor.NetworkModel.PatientList;
import com.nextgenit.doctor.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardListiewHolder> {


    private Activity mActivity = null;
    private ArrayList<PatientList> messageEntities;
    private IClickListener iClickListener;
    public DashboardAdapter(Activity activity, ArrayList<PatientList> messageEntitie,IClickListener iClickListeners) {
        mActivity = activity;
        messageEntities = messageEntitie;
        iClickListener=iClickListeners;
    }


    @Override
    public DashboardAdapter.DashboardListiewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_dashboard_list, null);
        return new DashboardAdapter.DashboardListiewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DashboardAdapter.DashboardListiewHolder holder, final int position) {

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
    }

    @Override
    public int getItemCount() {
        Log.e("evan", "sd" + messageEntities.size());
        return messageEntities.size();
    }

    public class DashboardListiewHolder extends RecyclerView.ViewHolder {

        private TextView tv_pharmacy_name;
        private TextView tv_name;
        private TextView tv_age;
        private TextView tv_phone_number;
        private TextView et_weight;
        private TextView et_height;
        private RoundedImageView user_icon;


        public DashboardListiewHolder(View itemView) {
            super(itemView);

            user_icon = itemView.findViewById(R.id.user_icon);
           tv_pharmacy_name = itemView.findViewById(R.id.tv_pharmacy_name);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_age = itemView.findViewById(R.id.tv_age);
            tv_phone_number = itemView.findViewById(R.id.tv_phone_number);
            et_weight = itemView.findViewById(R.id.et_weight);
            et_height = itemView.findViewById(R.id.et_height);


        }
    }
}