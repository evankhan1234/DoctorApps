package com.nextgenit.doctor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nextgenit.doctor.Adapter.DashboardAdapter;
import com.nextgenit.doctor.Adapter.DiagnosisAdapter;
import com.nextgenit.doctor.Adapter.RxDoseInstructionAdapter;
import com.nextgenit.doctor.Adapter.ViewAdviseAdapter;
import com.nextgenit.doctor.Adapter.ViewDiagnosisAdapter;
import com.nextgenit.doctor.Adapter.ViewPathologyAdapter;
import com.nextgenit.doctor.NetworkModel.PatientList;
import com.nextgenit.doctor.R;
import com.nextgenit.doctor.Utils.SharedPreferenceUtil;

import java.util.ArrayList;

import static com.nextgenit.doctor.Activity.PrescriptionEngineActivity.arrayList;

public class PrescriptionViewActivity extends AppCompatActivity {

    ArrayList<String> dose;
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
    PatientList patientList;
    TextView tv_patient_name;
    TextView tv_patient_details;
    TextView tv_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_view);
        rc_rx_duration=findViewById(R.id.rc_rx_duration);
        rc_rx_diagnosis=findViewById(R.id.rc_rx_diagnosis);
        rc_advise=findViewById(R.id.rc_advise);
        rc_investigation=findViewById(R.id.rc_investigation);
        tv_patient_name=findViewById(R.id.tv_patient_name);
        tv_patient_details=findViewById(R.id.tv_patient_details);
        tv_name=findViewById(R.id.tv_name);
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
        instruction = getIntent().getStringArrayListExtra("instruction");
        diagnosis = getIntent().getStringArrayListExtra("diagnosis");
        investigation = getIntent().getStringArrayListExtra("investigation");
        advise = getIntent().getStringArrayListExtra("advise");
        medication = getIntent().getStringArrayListExtra("medication");
        Log.e("dose","dose"+new Gson().toJson(advise));
        rxDoseInstructionAdapter = new RxDoseInstructionAdapter(PrescriptionViewActivity.this, dose,instruction,medication);
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
    }
}