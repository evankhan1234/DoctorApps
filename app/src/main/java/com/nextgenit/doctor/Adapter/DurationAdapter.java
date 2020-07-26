package com.nextgenit.doctor.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nextgenit.doctor.Filterable.SpinnerDose;
import com.nextgenit.doctor.Filterable.SpinnerDuration;
import com.nextgenit.doctor.Interface.DoseInterface;
import com.nextgenit.doctor.Interface.DurationInterface;
import com.nextgenit.doctor.R;

import java.util.List;

public class DurationAdapter extends RecyclerView.Adapter<DurationAdapter.PlaceTagListiewHolder> implements Filterable {


    SpinnerDuration filter;
    private Activity mActivity = null;
    public List<String> messageEntities;
    DurationInterface bookItemInterfaces;
    String types;
    String quantity;


    public DurationAdapter(Activity activity, List<String> messageEntitie, DurationInterface bookItemInterface,String type,String quantity) {
        mActivity = activity;
        this.messageEntities = messageEntitie;
        this.bookItemInterfaces = bookItemInterface;
        this.types = type;
        this.quantity = quantity;


    }


    @Override
    public DurationAdapter.PlaceTagListiewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_investigation_item, null);

        return new DurationAdapter.PlaceTagListiewHolder(view);
    }

    @Override
    public void onBindViewHolder(final DurationAdapter.PlaceTagListiewHolder holder, final int position) {
        // UserList messageEntitie= messageEntities.get(position);
        holder.text1.setText(quantity+" "+messageEntities.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookItemInterfaces.postion(messageEntities.get(position),types);
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
            filter = new SpinnerDuration(messageEntities, this);
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