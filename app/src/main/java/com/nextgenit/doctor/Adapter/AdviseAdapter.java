package com.nextgenit.doctor.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nextgenit.doctor.Filterable.SpinnerAdvise;
import com.nextgenit.doctor.Filterable.SpinnerDose;
import com.nextgenit.doctor.Interface.AdviseInterface;
import com.nextgenit.doctor.Interface.DoseInterface;
import com.nextgenit.doctor.R;

import java.util.List;

public class AdviseAdapter extends RecyclerView.Adapter<AdviseAdapter.PlaceTagListiewHolder> implements Filterable {


    SpinnerAdvise filter;
    private Activity mActivity = null;
    public List<String> messageEntities;
    AdviseInterface bookItemInterfaces;
    String types;


    public AdviseAdapter(Activity activity, List<String> messageEntitie, AdviseInterface bookItemInterface,String type) {
        mActivity = activity;
        this.messageEntities = messageEntitie;
        this.bookItemInterfaces = bookItemInterface;
        this.types = type;


    }


    @Override
    public AdviseAdapter.PlaceTagListiewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_investigation_item, null);

        return new AdviseAdapter.PlaceTagListiewHolder(view);
    }

    @Override
    public void onBindViewHolder(final AdviseAdapter.PlaceTagListiewHolder holder, final int position) {
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
            filter = new SpinnerAdvise(messageEntities, this);
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