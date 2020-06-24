package com.nextgenit.doctor.Adapter;

import android.app.Activity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.nextgenit.doctor.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardListiewHolder> {


    private Activity mActivity = null;
    private ArrayList<String> messageEntities;
    
    public DashboardAdapter(Activity activity, ArrayList<String> messageEntitie) {
        mActivity = activity;
        messageEntities = messageEntitie;
     
    }


    @Override
    public DashboardAdapter.DashboardListiewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_dashboard_list, null);
        return new DashboardAdapter.DashboardListiewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DashboardAdapter.DashboardListiewHolder holder, final int position) {


        Log.e("Evan", "SDfs" + messageEntities.get(position));
       // holder.btn_Dashboard.setHint(messageEntities.get(position).DashboardName);
        String text = "<b><font color=#000 >রোগের বিবরণ : </font></b> <font color=#358ED3>এখন থেকে শুধু আপনি সাইন-ইন করে থাকলেই অনুবাদের ইতিহাস উপলভ্য হবে এবং সেটিকে আমার অ্যাক্টিভিটি বিকল্প থেকেই ম্যানেজ করা হবে ।</font>";

        holder.tv_details.setText(Html.fromHtml(text));

        Glide.with(mActivity).load("https://www.hardiagedcare.com.au/wp-content/uploads/2019/02/default-avatar-profile-icon-vector-18942381.jpg").placeholder(R.mipmap.ic_launcher).into(holder.user_icon);

    }

    @Override
    public int getItemCount() {
        Log.e("evan", "sd" + messageEntities.size());
        return messageEntities.size();
    }

    public class DashboardListiewHolder extends RecyclerView.ViewHolder {

        private TextView tv_details;
        private CircleImageView user_icon;


        public DashboardListiewHolder(View itemView) {
            super(itemView);
            tv_details = itemView.findViewById(R.id.tv_details);
            user_icon = itemView.findViewById(R.id.user_icon);


        }
    }
}