package com.nextgenit.doctor.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nextgenit.doctor.R;

import java.util.ArrayList;

public class ViewDiagnosisAdapter extends RecyclerView.Adapter<ViewDiagnosisAdapter.PlaceTagListiewHolder> {

    private Activity mActivity = null;
    public ArrayList<String> doesEntities;


    public ViewDiagnosisAdapter(Activity activity, ArrayList<String> messageEntitie) {
        mActivity = activity;
        this.doesEntities = messageEntitie;


    }

    @Override
    public ViewDiagnosisAdapter.PlaceTagListiewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list, null);
        return new ViewDiagnosisAdapter.PlaceTagListiewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewDiagnosisAdapter.PlaceTagListiewHolder holder, final int position) {
        holder.tv_dose.setText(doesEntities.get(position));
    }

    @Override
    public int getItemCount() {
        return doesEntities.size();
    }

    public class PlaceTagListiewHolder extends RecyclerView.ViewHolder {
        private TextView tv_dose;

        public PlaceTagListiewHolder(View itemView) {
            super(itemView);
            tv_dose = itemView.findViewById(R.id.tv_dose);
        }
    }

}
