package com.nextgenit.doctor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.nextgenit.doctor.Adapter.DashboardAdapter;
import com.nextgenit.doctor.Network.IRetrofitApi;
import com.nextgenit.doctor.NetworkModel.PatientListResponses;
import com.nextgenit.doctor.NetworkModel.Pharmacy;
import com.nextgenit.doctor.NetworkModel.PharmacyListResponses;
import com.nextgenit.doctor.NetworkModel.Specialist;
import com.nextgenit.doctor.NetworkModel.SpecialistResponses;
import com.nextgenit.doctor.R;
import com.nextgenit.doctor.Utils.Common;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DashboardActivity extends AppCompatActivity {
    private RecyclerView rcv_list;
    private DashboardAdapter dashboardAdapter = null;
    private Activity mActivity;
    ArrayList<String> data= new ArrayList<>();
    ArrayList<Pharmacy> pharmacyArrayList;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofitApi mService;
    ProgressBar progress_bar;
    ArrayAdapter<Pharmacy> specialistArrayAdapter;
    Spinner spinner_pharmacy;
    int pharmacyId;
    SwipyRefreshLayout swipe_refresh;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mService= Common.getApiXact();
        rcv_list=findViewById(R.id.rcv_list);
        spinner_pharmacy=findViewById(R.id.spinner_pharmacy);
        swipe_refresh=findViewById(R.id.swipe_refresh);
        progress_bar=findViewById(R.id.progress_bar);
        mActivity=this;
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rcv_list.setLayoutManager(lm);

        swipe_refresh.setOnRefreshListener(new SwipyRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh(SwipyRefreshLayoutDirection direction) {
                loadDataAll();
                swipe_refresh.setRefreshing(false);
            }
        });
        spinner_pharmacy.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("sp_water", "" + pharmacyArrayList.get(position).pharmacy_id);
                pharmacyId = pharmacyArrayList.get(position).pharmacy_id;
                if (pharmacyId==-1){
                    loadDataAll();
                }
                else{
                    loadData(pharmacyId);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void loadDataAll() {
        progress_bar.setVisibility(View.VISIBLE);
        compositeDisposable.add(mService.getPatientListAll().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<PatientListResponses>() {
            @Override
            public void accept(PatientListResponses patientListResponses) throws Exception {
                Log.e("study", "study" + new Gson().toJson(patientListResponses));
                dashboardAdapter = new DashboardAdapter(mActivity, patientListResponses.data_list);

                rcv_list.setAdapter(dashboardAdapter);
                progress_bar.setVisibility(View.GONE);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("study", "study" + throwable.getMessage());
                progress_bar.setVisibility(View.GONE);
            }
        }));

    }
    private void loadData(int pharmacyId) {
        progress_bar.setVisibility(View.VISIBLE);
        compositeDisposable.add(mService.getPatientList(pharmacyId).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<PatientListResponses>() {
            @Override
            public void accept(PatientListResponses patientListResponses) throws Exception {
                Log.e("study", "study" + new Gson().toJson(patientListResponses));
                dashboardAdapter = new DashboardAdapter(mActivity, patientListResponses.data_list);

                rcv_list.setAdapter(dashboardAdapter);
                progress_bar.setVisibility(View.GONE);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("study", "study" + throwable.getMessage());
                progress_bar.setVisibility(View.GONE);
            }
        }));

    }
    private void loadDatas() {
        progress_bar.setVisibility(View.VISIBLE);
        compositeDisposable.add(mService.getPharmacy().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<PharmacyListResponses>() {
            @Override
            public void accept(PharmacyListResponses pharmacyListResponses) throws Exception {
                pharmacyArrayList=pharmacyListResponses.data_list;
                specialistArrayAdapter = new ArrayAdapter<>(DashboardActivity.this, android.R.layout.simple_spinner_item, pharmacyListResponses.data_list);
                specialistArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_pharmacy.setAdapter(specialistArrayAdapter);
                progress_bar.setVisibility(View.GONE);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("study", "study" + throwable.getMessage());
                progress_bar.setVisibility(View.GONE);
            }
        }));

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDatas();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    @Override
    public void onStop() {
        super.onStop();
        compositeDisposable.clear();
    }
}