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
import com.nextgenit.doctor.Interface.AdviseInterface;
import com.nextgenit.doctor.R;

import java.util.ArrayList;
import java.util.List;

public class RxDoseInstructionAdapter extends RecyclerView.Adapter<RxDoseInstructionAdapter.PlaceTagListiewHolder>  {


    private Activity mActivity = null;
    public ArrayList<String> doesEntities;
    public ArrayList<String> instructionEntities;
    public ArrayList<String> medicationEntities;
    public ArrayList<String> duration;
    public ArrayList<String> durationSecond;


    public RxDoseInstructionAdapter(Activity activity, ArrayList<String> messageEntitie,ArrayList<String> instructionEntitie,ArrayList<String> medicationEntitie,ArrayList<String> duration,ArrayList<String> durationSecond) {
        mActivity = activity;
        this.doesEntities = messageEntitie;
        this.instructionEntities = instructionEntitie;
        this.medicationEntities = medicationEntitie;
        this.duration = duration;
        this.durationSecond = durationSecond;


    }


    @Override
    public RxDoseInstructionAdapter.PlaceTagListiewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_rx_list, null);

        return new RxDoseInstructionAdapter.PlaceTagListiewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RxDoseInstructionAdapter.PlaceTagListiewHolder holder, final int position) {

        holder.tv_instruction.setText(doesEntities.get(position)+" "+instructionEntities.get(position)+" ("+duration.get(position)+" "+durationSecond.get(position)+")");
        holder.tv_dose.setText(medicationEntities.get(position));



    }

    @Override
    public int getItemCount() {

        return medicationEntities.size();
    }


    public class PlaceTagListiewHolder extends RecyclerView.ViewHolder {

        private TextView tv_dose;
        private TextView tv_instruction;



        public PlaceTagListiewHolder(View itemView) {
            super(itemView);

            tv_dose = itemView.findViewById(R.id.tv_dose);
            tv_instruction = itemView.findViewById(R.id.tv_instruction);



        }
    }




}