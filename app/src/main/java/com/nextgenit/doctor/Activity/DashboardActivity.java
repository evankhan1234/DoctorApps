package com.nextgenit.doctor.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.nextgenit.doctor.Adapter.DashboardAdapter;
import com.nextgenit.doctor.Interface.IClickListener;
import com.nextgenit.doctor.Interface.IPharmacyClickListener;
import com.nextgenit.doctor.Network.IRetrofitApi;
import com.nextgenit.doctor.NetworkModel.ContentResponses;
import com.nextgenit.doctor.NetworkModel.NewPatientList;
import com.nextgenit.doctor.NetworkModel.PatientList;
import com.nextgenit.doctor.NetworkModel.PatientListResponses;
import com.nextgenit.doctor.NetworkModel.Pharmacy;
import com.nextgenit.doctor.NetworkModel.PharmacyListResponses;
import com.nextgenit.doctor.NetworkModel.Specialist;
import com.nextgenit.doctor.NetworkModel.SpecialistResponses;
import com.nextgenit.doctor.R;
import com.nextgenit.doctor.Utils.Common;
import com.nextgenit.doctor.Utils.SharedPreferenceUtil;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class DashboardActivity extends AppCompatActivity {
    private RecyclerView rcv_list;
    private DashboardAdapter dashboardAdapter = null;
    private Activity mActivity;
    ArrayList<String> data = new ArrayList<>();
    ArrayList<Pharmacy> pharmacyArrayList;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofitApi mService;
    ProgressBar progress_bar;
    ArrayAdapter<Pharmacy> specialistArrayAdapter;
    Spinner spinner_pharmacy;
    int pharmacyId;
    SwipyRefreshLayout swipe_refresh;
    ImageView img_log_out;
    DatabaseReference reference;
    private DatabaseReference userRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mService = Common.getApiXact();
        rcv_list = findViewById(R.id.rcv_list);
        img_log_out = findViewById(R.id.img_log_out);
        spinner_pharmacy = findViewById(R.id.spinner_pharmacy);
        swipe_refresh = findViewById(R.id.swipe_refresh);
        progress_bar = findViewById(R.id.progress_bar);
        mActivity = this;
       // requestPermission();
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rcv_list.setLayoutManager(lm);
        img_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
                SharedPreferenceUtil.saveShared(DashboardActivity.this, SharedPreferenceUtil.TYPE_USER_ID, "");
                finish();
            }
        });
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
                SharedPreferenceUtil.saveShared(DashboardActivity.this, SharedPreferenceUtil.PHARMACY_ID, pharmacyId + "");

                if (pharmacyId == -1) {
                    loadDataAll();
                } else {
                    loadData(pharmacyId);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    private final int CAMERA_PERMISSION_REQUEST_CODE =  2;

    IPharmacyClickListener pharmacyClickListener = new IPharmacyClickListener() {
        @Override
        public void onClick(int pharmacyId) {
            loadVideoData(pharmacyId);

        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 786 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

        }

    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 786);
        } else {

        }
    }

    private void loadDataAll() {
        progress_bar.setVisibility(View.VISIBLE);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date12 = new Date(System.currentTimeMillis());
        String currentDate = formatter.format(date12);
        compositeDisposable.add(mService.getNewPatientList(0, Integer.parseInt(SharedPreferenceUtil.getUserID(DashboardActivity.this)), currentDate).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<PatientListResponses>() {
            @Override
            public void accept(PatientListResponses patientListResponses) throws Exception {
                Log.e("study", "study" + new Gson().toJson(patientListResponses));
                dashboardAdapter = new DashboardAdapter(mActivity, patientListResponses.data_list, iClickListener, pharmacyClickListener);

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
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date date12 = new Date(System.currentTimeMillis());
        String currentDate = formatter.format(date12);
        compositeDisposable.add(mService.getNewPatientList(pharmacyId, Integer.parseInt(SharedPreferenceUtil.getUserID(DashboardActivity.this)), currentDate).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<PatientListResponses>() {
            @Override
            public void accept(PatientListResponses patientListResponses) throws Exception {
                Log.e("study", "study" + new Gson().toJson(patientListResponses));
                dashboardAdapter = new DashboardAdapter(mActivity, patientListResponses.data_list, iClickListener, pharmacyClickListener);

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

    String userId="";
    private void loadVideoData(int pharmacyId) {
        progress_bar.setVisibility(View.VISIBLE);

        compositeDisposable.add(mService.getVideoContent(pharmacyId).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<ContentResponses>() {
            @Override
            public void accept(ContentResponses contentResponses) throws Exception {
                Log.e("study", "study" + new Gson().toJson(contentResponses));
                progress_bar.setVisibility(View.GONE);

                userRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Log.e("snapshot","value"+ snapshot.child("type").getValue().toString());

                        String type=snapshot.child("type").getValue().toString();
                        if (type.equals("Accept")){
                            Toast.makeText(mActivity, "Already in Call", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            userId=contentResponses.data_list.content;
                            status(contentResponses.data_list.content);
                            Intent intent= new Intent(DashboardActivity.this, CallingActivity.class);
                            intent.putExtra("value",userId);
                            startActivity(intent);
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("study", "study" + throwable.getMessage());
                progress_bar.setVisibility(View.GONE);
            }
        }));

    }
    private void status(String status){
        reference = FirebaseDatabase.getInstance().getReference("Users").child(status);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("type", "Ringing");

        reference.updateChildren(hashMap);
    }
    private void loadDatas() {
        progress_bar.setVisibility(View.VISIBLE);
        compositeDisposable.add(mService.getPharmacy().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<PharmacyListResponses>() {
            @Override
            public void accept(PharmacyListResponses pharmacyListResponses) throws Exception {
                pharmacyArrayList = pharmacyListResponses.data_list;
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

    private IClickListener iClickListener = new IClickListener() {
        @Override
        public void show(NewPatientList patientList) {
            if (patientList.prescription_no_pk==null){
                Intent intent = new Intent(DashboardActivity.this, PrescriptionViewAgainActivity.class);
                intent.putExtra("id", Integer.parseInt(patientList.prescription_no_pk));
                startActivity(intent);
            }
            else{
                Intent intent = new Intent(DashboardActivity.this, PrescriptionEngineActivity.class);
                intent.putExtra("patient", patientList);
                startActivity(intent);
            }

        }
    };

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