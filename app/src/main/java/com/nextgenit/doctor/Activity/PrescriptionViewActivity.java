package com.nextgenit.doctor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nextgenit.doctor.Adapter.DashboardAdapter;
import com.nextgenit.doctor.Adapter.DiagnosisAdapter;
import com.nextgenit.doctor.Adapter.RxDoseInstructionAdapter;
import com.nextgenit.doctor.Adapter.ViewAdviseAdapter;
import com.nextgenit.doctor.Adapter.ViewDiagnosisAdapter;
import com.nextgenit.doctor.Adapter.ViewPathologyAdapter;
import com.nextgenit.doctor.LocalModel.PrescriptionModel;
import com.nextgenit.doctor.Network.IRetrofitApi;
import com.nextgenit.doctor.NetworkModel.APIResponses;
import com.nextgenit.doctor.NetworkModel.NewPatientList;
import com.nextgenit.doctor.NetworkModel.PatientList;
import com.nextgenit.doctor.NetworkModel.RegistrationResponses;
import com.nextgenit.doctor.R;
import com.nextgenit.doctor.Utils.Common;
import com.nextgenit.doctor.Utils.SharedPreferenceUtil;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.nextgenit.doctor.Activity.PrescriptionEngineActivity.arrayList;

public class PrescriptionViewActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofitApi mService;
    ArrayList<String> dose;
    ArrayList<String> duration;
    ArrayList<String> durationSecond;
    ArrayList<String> instruction;
    ArrayList<String> diagnosis;
    ArrayList<String> investigation;
    ArrayList<String> advise;
    ArrayList<String> medication;
    RxDoseInstructionAdapter rxDoseInstructionAdapter;
    ViewDiagnosisAdapter diagnosisAdapter;
    ViewPathologyAdapter pathologyAdapter;
    ViewAdviseAdapter viewAdviseAdapter;

    RecyclerView rc_rx_duration;
    RecyclerView rc_rx_diagnosis;
    RecyclerView rc_investigation;
    RecyclerView rc_advise;
    NewPatientList patientList;
    TextView tv_patient_name;
    TextView tv_patient_details;
    TextView tv_name;
    Button btn_done;
    ProgressBar progress_bar;
    String data="";
    ImageView img_log_out;
    ImageView img_close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_view);
        btn_done=findViewById(R.id.btn_done);
        progress_bar=findViewById(R.id.progress_bar);
        mService= Common.getApiXact();
        rc_rx_duration=findViewById(R.id.rc_rx_duration);
        rc_rx_diagnosis=findViewById(R.id.rc_rx_diagnosis);
        rc_advise=findViewById(R.id.rc_advise);
        rc_investigation=findViewById(R.id.rc_investigation);
        tv_patient_name=findViewById(R.id.tv_patient_name);
        tv_patient_details=findViewById(R.id.tv_patient_details);
        tv_name=findViewById(R.id.tv_name);
        img_log_out=findViewById(R.id.img_log_out);
        img_close=findViewById(R.id.img_close);
        img_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PrescriptionViewActivity.this,LoginActivity.class));
                SharedPreferenceUtil.saveShared(PrescriptionViewActivity.this, SharedPreferenceUtil.TYPE_USER_ID,  "");
                finish();
            }
        });
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrescriptionEngineActivity.onClear();
                startActivity(new Intent(PrescriptionViewActivity.this,DashboardActivity.class));
                finish();
            }
        });
        LinearLayoutManager lm1 = new LinearLayoutManager(this);
        LinearLayoutManager lm2 = new LinearLayoutManager(this);
        LinearLayoutManager lm3 = new LinearLayoutManager(this);
        LinearLayoutManager lm4 = new LinearLayoutManager(this);
        lm1.setOrientation(LinearLayoutManager.VERTICAL);
        lm2.setOrientation(LinearLayoutManager.VERTICAL);
        lm3.setOrientation(LinearLayoutManager.VERTICAL);
        lm4.setOrientation(LinearLayoutManager.VERTICAL);
        rc_rx_duration.setLayoutManager(lm1);
        rc_rx_diagnosis.setLayoutManager(lm2);
        rc_investigation.setLayoutManager(lm3);
        rc_advise.setLayoutManager(lm4);
        patientList = getIntent().getExtras().getParcelable("patient");
        dose = getIntent().getStringArrayListExtra("dose");
        duration = getIntent().getStringArrayListExtra("duration");
        durationSecond = getIntent().getStringArrayListExtra("durationSecond");
        instruction = getIntent().getStringArrayListExtra("instruction");
        diagnosis = getIntent().getStringArrayListExtra("diagnosis");
        investigation = getIntent().getStringArrayListExtra("investigation");
        advise = getIntent().getStringArrayListExtra("advise");
        medication = getIntent().getStringArrayListExtra("medication");
      //  Log.e("dose","dose"+new Gson().toJson(advise));
        rxDoseInstructionAdapter = new RxDoseInstructionAdapter(PrescriptionViewActivity.this, dose,instruction,medication,duration,durationSecond);
        rc_rx_duration.setAdapter(rxDoseInstructionAdapter);
        diagnosisAdapter = new ViewDiagnosisAdapter(PrescriptionViewActivity.this, diagnosis);
        rc_rx_diagnosis.setAdapter(diagnosisAdapter);
        pathologyAdapter = new ViewPathologyAdapter(PrescriptionViewActivity.this, investigation);
        rc_investigation.setAdapter(pathologyAdapter);
        viewAdviseAdapter = new ViewAdviseAdapter(PrescriptionViewActivity.this, advise);
        rc_advise.setAdapter(viewAdviseAdapter);
        tv_patient_name.setText(patientList.patient_name);
        tv_name.setText(SharedPreferenceUtil.getUserName(PrescriptionViewActivity.this));
        tv_patient_details.setText("Age - "+patientList.age+", "+patientList.gender_txt+", Wt-"+patientList.initial_weight+"kg, "+"Ht-"+patientList.initial_weight+"");
        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoad();
            }
        });
        data=SharedPreferenceUtil.getData(PrescriptionViewActivity.this);
        Log.e("Value","Value"+SharedPreferenceUtil.getData(PrescriptionViewActivity.this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        PrescriptionEngineActivity.onClear();
        startActivity(new Intent(PrescriptionViewActivity.this,DashboardActivity.class));
        finish();

    }

    private void onLoad(){
        progress_bar.setVisibility(View.VISIBLE);
        compositeDisposable.add(mService.postPrescription(data).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<APIResponses>() {
            @Override
            public void accept(APIResponses apiResponses) throws Exception {
                Log.e("ff", "dgg" + new Gson().toJson(apiResponses));

                progress_bar.setVisibility(View.GONE);
                if (apiResponses.status.equals("success")) {
                    //    SharedPreferenceUtil.saveShared(RegistrationActivity.this, SharedPreferenceUtil.TYPE_USER_ID, registrationResponses.user.user_no_pk + "");
                    Toast.makeText(PrescriptionViewActivity.this, "Successfully Created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PrescriptionViewActivity.this,DashboardActivity.class));
                    finish();

                     PrescriptionEngineActivity.onClear();

                }
                else if (apiResponses.status.equals("failed")){
                    Toast.makeText(PrescriptionViewActivity.this, apiResponses.message, Toast.LENGTH_SHORT).show();

                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                progress_bar.setVisibility(View.GONE);
                Log.e("ff", "dgg" + throwable.getMessage());
                Toast.makeText(PrescriptionViewActivity.this, "Unauthorized", Toast.LENGTH_SHORT).show();

            }
        }));
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