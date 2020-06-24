package com.nextgenit.doctor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;

import com.nextgenit.doctor.Adapter.DashboardAdapter;
import com.nextgenit.doctor.R;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
    private RecyclerView rcv_list;
    private DashboardAdapter dashboardAdapter = null;
    private Activity mActivity;
    ArrayList<String> data= new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rcv_list=findViewById(R.id.rcv_list);
        mActivity=this;
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rcv_list.setLayoutManager(lm);

        loadData();

    }

    private void loadData() {

        for (int i=0;i<10;i++){
            data.add("String"+i);
        }
        dashboardAdapter = new DashboardAdapter(mActivity, data);

        rcv_list.setAdapter(dashboardAdapter);

    }
}