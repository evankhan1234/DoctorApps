package com.nextgenit.doctor.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.nextgenit.doctor.Activity.PrescriptionEngineActivity;
import com.nextgenit.doctor.R;

import java.util.ArrayList;

public class DurationRemoveOrAddAdapter extends RecyclerView.Adapter<DurationRemoveOrAddAdapter.TagListiewHolder> {


    private Activity mActivity = null;
    private ArrayList<String> messageEntities;
    private ArrayList<String> messageDuration;
    String types;

    public DurationRemoveOrAddAdapter(Activity activity, ArrayList<String> messageEntitie,ArrayList<String> messageDurations, String type) {
        mActivity = activity;
        messageEntities = messageEntitie;
        types = type;
        messageDuration = messageDurations;
    }


    @Override
    public DurationRemoveOrAddAdapter.TagListiewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_investigation_remove_or_add, null);


        return new DurationRemoveOrAddAdapter.TagListiewHolder(view);
    }

    @Override
    public void onBindViewHolder(DurationRemoveOrAddAdapter.TagListiewHolder holder, final int position) {

        if (types.equals("D")) {
            holder.mButtonTagList.setText(messageEntities.get(position)+" "+messageDuration.get(position));
            holder.mButtonSelectImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    messageEntities.remove(position);
                    notifyDataSetChanged();
                    PrescriptionEngineActivity.DurationShow(position);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        Log.e("dsd", "sd" + messageEntities.size());
        return messageEntities.size();
    }

    public class TagListiewHolder extends RecyclerView.ViewHolder {
        private TextView mButtonTagList = null;
        private ImageButton mButtonSelectImage = null;


        public TagListiewHolder(View itemView) {
            super(itemView);
            mButtonTagList = (TextView) itemView.findViewById(R.id.btn_tag);
            mButtonSelectImage = (ImageButton) itemView.findViewById(R.id.btn_select_image);


        }
    }
}
