package com.nextgenit.doctor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nextgenit.doctor.Adapter.RxDoseInstructionAgainAdapter;
import com.nextgenit.doctor.Adapter.ViewAdviseAgainAdapter;
import com.nextgenit.doctor.Adapter.ViewDiagnosisAgainAdapter;
import com.nextgenit.doctor.Adapter.ViewPathologyAgainAdapter;
import com.nextgenit.doctor.Network.IRetrofitApi;
import com.nextgenit.doctor.NetworkModel.DetailsList;
import com.nextgenit.doctor.NetworkModel.MedList;
import com.nextgenit.doctor.NetworkModel.PrescriptionListHeaderResponses;
import com.nextgenit.doctor.NetworkModel.PresecriptionViewResponses;
import com.nextgenit.doctor.R;
import com.nextgenit.doctor.Utils.Common;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PrescriptionViewAgainActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofitApi mService;
    RecyclerView rc_rx_duration;
    RecyclerView rc_rx_diagnosis;
    RecyclerView rc_investigation;
    RecyclerView rc_advise;
    TextView tv_patient_name;
    TextView tv_patient_details;
    TextView tv_name;
    TextView tv_degree;
    ProgressBar progress_bar;
    String data="";
    int id;
    RxDoseInstructionAgainAdapter rxDoseInstructionAdapter;
    ViewDiagnosisAgainAdapter diagnosisAdapter;
    ViewPathologyAgainAdapter pathologyAdapter;
    ViewAdviseAgainAdapter viewAdviseAdapter;
    ArrayList<String> advise= new ArrayList<>();
    ArrayList<String> investigation= new ArrayList<>();
    ArrayList<String> diagnosis= new ArrayList<>();
    ArrayList<String> dose= new ArrayList<>();
    ArrayList<String> instruction= new ArrayList<>();
    ArrayList<String> medication= new ArrayList<>();
    ImageView img_close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_view_again);
        id = getIntent().getIntExtra("id",0);
        progress_bar=findViewById(R.id.progress_bar);
        mService= Common.getApiXact();
        rc_rx_duration=findViewById(R.id.rc_rx_duration);
        tv_degree=findViewById(R.id.tv_degree);
        rc_rx_diagnosis=findViewById(R.id.rc_rx_diagnosis);
        rc_advise=findViewById(R.id.rc_advise);
        rc_investigation=findViewById(R.id.rc_investigation);
        tv_patient_name=findViewById(R.id.tv_patient_name);
        tv_patient_details=findViewById(R.id.tv_patient_details);
        tv_name=findViewById(R.id.tv_name);
        img_close=findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    }

    private void loadDataAll() {
        progress_bar.setVisibility(View.VISIBLE);

        compositeDisposable.add(mService.getPrescriptionViewHeader(id).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<PresecriptionViewResponses>() {
            @Override
            public void accept(PresecriptionViewResponses presecriptionListResponses) throws Exception {
                Log.e("getPrescriptionList", "getPrescriptionList" + new Gson().toJson(presecriptionListResponses));
                tv_patient_name.setText(presecriptionListResponses.data_list.patient_name);
                tv_name.setText(presecriptionListResponses.data_list.doctor_name);
                tv_patient_details.setText("Age - "+presecriptionListResponses.data_list.age+", "+presecriptionListResponses.data_list.gender_txt);
                if (presecriptionListResponses.data_list.degree1!=null){
                    tv_degree.setText(presecriptionListResponses.data_list.degree1+","+presecriptionListResponses.data_list.degree2);
                }
                else{
                    tv_degree.setText("");
                }

                progress_bar.setVisibility(View.GONE);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("getPrescriptionList", "getPrescriptionList" + throwable.getMessage());
                progress_bar.setVisibility(View.GONE);
            }
        }));

    }
    private void loadData() {
        progress_bar.setVisibility(View.VISIBLE);

        compositeDisposable.add(mService.getPrescriptionViewHeaderDetails(id).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<PrescriptionListHeaderResponses>() {
            @Override
            public void accept(PrescriptionListHeaderResponses presecriptionListResponses) throws Exception {
                Log.e("getPrescriptionList", "getPrescriptionList" + new Gson().toJson(presecriptionListResponses));

                for (DetailsList details :presecriptionListResponses.details_data){
                    if (details.itemtype_lookupdata_code.equals("ADVICE")){
                        advise.add(details.presdtl_data);
                    }
                    else if (details.itemtype_lookupdata_code.equals("DIAGNOSIS")){
                        diagnosis.add(details.presdtl_data);
                    }
                    else if (details.itemtype_lookupdata_code.equals("PATH")){
                        investigation.add(details.presdtl_data);
                    }
                }

                for (MedList medList:presecriptionListResponses.med_data){
                    dose.add(medList.dosage);
                    medication.add(medList.brand_name);
                    instruction.add(medList.relation_with_meal+" ("+medList.duration+" "+medList.duration_mu+")");
                }
                rxDoseInstructionAdapter = new RxDoseInstructionAgainAdapter(PrescriptionViewAgainActivity.this, dose,instruction,medication);
                rc_rx_duration.setAdapter(rxDoseInstructionAdapter);
                diagnosisAdapter = new ViewDiagnosisAgainAdapter(PrescriptionViewAgainActivity.this, diagnosis);
                rc_rx_diagnosis.setAdapter(diagnosisAdapter);
                viewAdviseAdapter = new ViewAdviseAgainAdapter(PrescriptionViewAgainActivity.this, advise);
                rc_advise.setAdapter(viewAdviseAdapter);
                pathologyAdapter = new ViewPathologyAgainAdapter(PrescriptionViewAgainActivity.this, investigation);
                rc_investigation.setAdapter(pathologyAdapter);
                progress_bar.setVisibility(View.GONE);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {

                progress_bar.setVisibility(View.GONE);
            }
        }));

    }
    @Override
    protected void onResume() {
        super.onResume();
        loadDataAll();
        loadData();
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