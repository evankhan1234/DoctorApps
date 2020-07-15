package com.nextgenit.doctor.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nextgenit.doctor.Filterable.SpinnerInvestigation;
import com.nextgenit.doctor.Filterable.SpinnerMedication;
import com.nextgenit.doctor.Interface.InvestigationInterface;
import com.nextgenit.doctor.Interface.MedicationInterface;
import com.nextgenit.doctor.R;

import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.PlaceTagListiewHolder> implements Filterable {


    SpinnerMedication filter;
    private Activity mActivity = null;
    public List<String> messageEntities;
    MedicationInterface bookItemInterfaces;
    String types;


    public MedicationAdapter(Activity activity, List<String> messageEntitie, MedicationInterface bookItemInterface,String type) {
        mActivity = activity;
        this.messageEntities = messageEntitie;
        this.bookItemInterfaces = bookItemInterface;
        this.types = type;


    }


    @Override
    public MedicationAdapter.PlaceTagListiewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_investigation_item, null);

        return new MedicationAdapter.PlaceTagListiewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MedicationAdapter.PlaceTagListiewHolder holder, final int position) {
        // UserList messageEntitie= messageEntities.get(position);
        holder.text1.setText(messageEntities.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookItemInterfaces.postion(position,types);
            }
        });


    }

    @Override
    public int getItemCount() {

        return messageEntities.size();
    }

    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new SpinnerMedication(messageEntities, this);
        }
        return filter;
    }

    public class PlaceTagListiewHolder extends RecyclerView.ViewHolder {

        private TextView text1;



        public PlaceTagListiewHolder(View itemView) {
            super(itemView);

            text1 = itemView.findViewById(R.id.text1);



        }
    }




}
