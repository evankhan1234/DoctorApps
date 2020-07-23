package com.nextgenit.doctor.Filterable;

import android.widget.Filter;

import com.nextgenit.doctor.Adapter.DoseAdapter;
import com.nextgenit.doctor.Adapter.InstructionAdapter;

import java.util.ArrayList;
import java.util.List;

public class SpinnerInstruction extends Filter {
    private List<String> items ;
    private InstructionAdapter adapter;

    public SpinnerInstruction(List<String> filterList, InstructionAdapter adapter) {
        this.adapter = adapter;
        this.items = filterList;
    }

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        Filter.FilterResults results = new Filter.FilterResults();

        if (constraint != null && constraint.length() > 0) {
            constraint = constraint.toString().toUpperCase();
            ArrayList<String> filteredPlayers = new ArrayList<>();
            for (int i = 0; i < items.size(); i++) {
                String   test = constraint.toString().replaceAll("\\p{P}","");
                if (items.get(i).toUpperCase().contains(constraint.toString())) {
                    filteredPlayers.add(items.get(i));
                }
            }
            results.count = filteredPlayers.size();
            results.values = filteredPlayers;
        } else {
            results.count = items.size();
            results.values = items;
        }
        return results;
    }
    SpinnerInvestigation filter;
    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        adapter.messageEntities = (ArrayList<String>) results.values;
        adapter.notifyDataSetChanged();
    }
}