package com.nextgenit.doctor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nextgenit.doctor.Adapter.DiagnosisRemoveOrAddAdapter;
import com.nextgenit.doctor.Adapter.InvestigationRemoveOrAddAdapter;
import com.nextgenit.doctor.Adapter.MedicationRemoveOrAddAdapter;
import com.nextgenit.doctor.Filterable.SpinnerForDiagnosis;
import com.nextgenit.doctor.Filterable.SpinnerForInvestigation;
import com.nextgenit.doctor.Filterable.SpinnerForMedication;
import com.nextgenit.doctor.Interface.DiagnosisInterface;
import com.nextgenit.doctor.Interface.DiagnosisTypeInterface;
import com.nextgenit.doctor.Interface.InvestigationInterface;
import com.nextgenit.doctor.Interface.InvestigationTypeInterface;
import com.nextgenit.doctor.Interface.MedicationInterface;
import com.nextgenit.doctor.Interface.MedicationTypeInterface;
import com.nextgenit.doctor.Network.IRetrofitApi;
import com.nextgenit.doctor.NetworkModel.Diagnosis;
import com.nextgenit.doctor.NetworkModel.DiagnosisListReponses;
import com.nextgenit.doctor.NetworkModel.Investigation;
import com.nextgenit.doctor.NetworkModel.InvestigationListResponses;
import com.nextgenit.doctor.NetworkModel.Medication;
import com.nextgenit.doctor.NetworkModel.MedicationListResponses;
import com.nextgenit.doctor.NetworkModel.PatientList;
import com.nextgenit.doctor.NetworkModel.Pharmacy;
import com.nextgenit.doctor.NetworkModel.PharmacyListResponses;
import com.nextgenit.doctor.R;
import com.nextgenit.doctor.Utils.Common;

import java.util.ArrayList;
import java.util.HashSet;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PrescriptionEngineActivity extends AppCompatActivity {
    PatientList patientList;
    TextView tv_investigation;
    TextView tv_diagnosis;
    TextView tv_medication;
    static  TextView tv_investigation_for;
    static  TextView tv_diagnosis_for;
    static  TextView tv_medication_for;
    SpinnerForInvestigation spinnerForInvestigation;
    SpinnerForDiagnosis spinnerForDiagnosis;
    SpinnerForMedication spinnerForMedication;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofitApi mService;
    ProgressBar progress_bar;
    ArrayList<String> investigationArrayList = new ArrayList<>();
    ArrayList<String> diagnosisArrayList = new ArrayList<>();
    ArrayList<String> medicationArrayList = new ArrayList<>();
    InvestigationRemoveOrAddAdapter investigationRemoveOrAddAdapter;
    MedicationRemoveOrAddAdapter medicationRemoveOrAddAdapter;
    DiagnosisRemoveOrAddAdapter diagnosisRemoveOrAddAdapter;
    static ArrayList<String> arrayList = new ArrayList<>();
    static ArrayList<String> arrayListDignosis = new ArrayList<>();
    static ArrayList<String> arrayListMedication = new ArrayList<>();
    static RecyclerView rc_investigation;
    static RecyclerView rc_diagnosis;
    static RecyclerView rc_medication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_engine);
        mService= Common.getApiXact();
        tv_medication_for=findViewById(R.id.tv_medication_for);
        tv_medication=findViewById(R.id.tv_medication);
        rc_investigation=findViewById(R.id.rc_investigation);
        tv_diagnosis_for=findViewById(R.id.tv_diagnosis_for);
        tv_diagnosis=findViewById(R.id.tv_diagnosis);
        rc_diagnosis=findViewById(R.id.rc_diagnosis);
        rc_medication=findViewById(R.id.rc_medication);
        tv_investigation_for=findViewById(R.id.tv_investigation_for);
        LinearLayoutManager lm1 = new LinearLayoutManager(this);
        LinearLayoutManager lm2 = new LinearLayoutManager(this);
        LinearLayoutManager lm3 = new LinearLayoutManager(this);
        lm1.setOrientation(LinearLayoutManager.VERTICAL);
        lm2.setOrientation(LinearLayoutManager.VERTICAL);
        lm3.setOrientation(LinearLayoutManager.VERTICAL);
        rc_investigation.setLayoutManager(lm1);
        rc_diagnosis.setLayoutManager(lm2);
        rc_medication.setLayoutManager(lm3);
        tv_investigation=findViewById(R.id.tv_investigation);
        progress_bar=findViewById(R.id.progress_bar);
        patientList = getIntent().getExtras().getParcelable("patient");

        tv_investigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerForInvestigation = new SpinnerForInvestigation(PrescriptionEngineActivity.this, investigationArrayList, "Select Investigation", investigationInterface, "D",investigationTypeInterfac);
                spinnerForInvestigation.showSpinerDialog();
            }
        });
        tv_diagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerForDiagnosis = new SpinnerForDiagnosis(PrescriptionEngineActivity.this, diagnosisArrayList, "Select Diagnosis", diagnosisInterface, "D",diagnosisTypeInterface);
                spinnerForDiagnosis.showSpinerDialog();
            }
        });
        tv_medication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerForMedication = new SpinnerForMedication(PrescriptionEngineActivity.this, medicationArrayList, "Select Medication", medicationInterface, "D",medicationTypeInterface);
                spinnerForMedication.showSpinerDialog();
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
        loadDiagnosisData();
        loadMedicationData();
    }

    private InvestigationInterface investigationInterface= new InvestigationInterface() {
        @Override
        public void postion(int position, String Type) {
            investigationRemoveOrAddAdapter = new InvestigationRemoveOrAddAdapter(PrescriptionEngineActivity.this, arrayList, "D");
            rc_investigation.setAdapter(investigationRemoveOrAddAdapter);
            spinnerForInvestigation.closeSpinerDialog();
            arrayList.add(investigationArrayList.get(position));
            HashSet hs = new HashSet();
            hs.addAll(arrayList);
            arrayList.clear();
            arrayList.addAll(hs);
            if (arrayList.size() > 0) {
                tv_investigation_for.setVisibility(View.GONE);
                rc_investigation.setVisibility(View.VISIBLE);
            } else {
                rc_investigation.setVisibility(View.GONE);
                tv_investigation_for.setVisibility(View.VISIBLE);
            }
            investigationRemoveOrAddAdapter.notifyDataSetChanged();
        }
    };
    private DiagnosisInterface diagnosisInterface= new DiagnosisInterface() {
        @Override
        public void postion(int position, String Type) {
            diagnosisRemoveOrAddAdapter = new DiagnosisRemoveOrAddAdapter(PrescriptionEngineActivity.this, arrayListDignosis, "D");
            rc_diagnosis.setAdapter(diagnosisRemoveOrAddAdapter);
            spinnerForDiagnosis.closeSpinerDialog();
            arrayListDignosis.add(diagnosisArrayList.get(position));
            HashSet hs = new HashSet();
            hs.addAll(arrayListDignosis);
            arrayListDignosis.clear();
            arrayListDignosis.addAll(hs);
            if (arrayListDignosis.size() > 0) {
                tv_diagnosis_for.setVisibility(View.GONE);
                rc_diagnosis.setVisibility(View.VISIBLE);
            } else {
                rc_diagnosis.setVisibility(View.GONE);
                tv_diagnosis_for.setVisibility(View.VISIBLE);
            }
            diagnosisRemoveOrAddAdapter.notifyDataSetChanged();
        }
    };
    private DiagnosisTypeInterface diagnosisTypeInterface= new DiagnosisTypeInterface() {
        @Override
        public void add(String type) {
            diagnosisRemoveOrAddAdapter = new DiagnosisRemoveOrAddAdapter(PrescriptionEngineActivity.this, arrayListDignosis, "D");
            rc_diagnosis.setAdapter(diagnosisRemoveOrAddAdapter);
            spinnerForDiagnosis.closeSpinerDialog();
            arrayListDignosis.add(type);
            HashSet hs = new HashSet();
            hs.addAll(arrayListDignosis);
            arrayListDignosis.clear();
            arrayListDignosis.addAll(hs);
            if (arrayListDignosis.size() > 0) {
                tv_diagnosis_for.setVisibility(View.GONE);
                rc_diagnosis.setVisibility(View.VISIBLE);
            } else {
                rc_diagnosis.setVisibility(View.GONE);
                tv_diagnosis_for.setVisibility(View.VISIBLE);
            }
            diagnosisRemoveOrAddAdapter.notifyDataSetChanged();
        }
    };
    public static void Show() {
        if (arrayList.size() > 0) {
            tv_investigation_for.setVisibility(View.GONE);
            rc_investigation.setVisibility(View.VISIBLE);
        } else {
            rc_investigation.setVisibility(View.GONE);
            tv_investigation_for.setVisibility(View.VISIBLE);
        }

    }
    public static void DiagnosisShow() {
        if (arrayListDignosis.size() > 0) {
            tv_diagnosis_for.setVisibility(View.GONE);
            rc_diagnosis.setVisibility(View.VISIBLE);
        } else {
            rc_diagnosis.setVisibility(View.GONE);
            tv_diagnosis_for.setVisibility(View.VISIBLE);
        }

    }
    public static void MedicationShow() {
        if (arrayListMedication.size() > 0) {
            tv_medication_for.setVisibility(View.GONE);
            rc_medication.setVisibility(View.VISIBLE);
        } else {
            rc_medication.setVisibility(View.GONE);
            tv_medication_for.setVisibility(View.VISIBLE);
        }

    }
    InvestigationTypeInterface investigationTypeInterfac = new InvestigationTypeInterface() {
        @Override
        public void add(String type) {
            investigationRemoveOrAddAdapter = new InvestigationRemoveOrAddAdapter(PrescriptionEngineActivity.this, arrayList, "D");
            rc_investigation.setAdapter(investigationRemoveOrAddAdapter);
            spinnerForInvestigation.closeSpinerDialog();
            arrayList.add(type);
            HashSet hs = new HashSet();
            hs.addAll(arrayList);
            arrayList.clear();
            arrayList.addAll(hs);
            if (arrayList.size() > 0) {
                tv_investigation_for.setVisibility(View.GONE);
                rc_investigation.setVisibility(View.VISIBLE);
            } else {
                rc_investigation.setVisibility(View.GONE);
                tv_investigation_for.setVisibility(View.VISIBLE);
            }
            investigationRemoveOrAddAdapter.notifyDataSetChanged();

        }
    };
    private MedicationInterface medicationInterface= new MedicationInterface() {
        @Override
        public void postion(int position, String Type) {
            medicationRemoveOrAddAdapter = new MedicationRemoveOrAddAdapter(PrescriptionEngineActivity.this, arrayListMedication, "D");
            rc_medication.setAdapter(medicationRemoveOrAddAdapter);
            spinnerForMedication.closeSpinerDialog();
            arrayListMedication.add(medicationArrayList.get(position));
            HashSet hs = new HashSet();
            hs.addAll(arrayListMedication);
            arrayListMedication.clear();
            arrayListMedication.addAll(hs);
            if (arrayListMedication.size() > 0) {
                tv_medication_for.setVisibility(View.GONE);
                rc_medication.setVisibility(View.VISIBLE);
            } else {
                rc_medication.setVisibility(View.GONE);
                tv_medication_for.setVisibility(View.VISIBLE);
            }
            medicationRemoveOrAddAdapter.notifyDataSetChanged();
        }
    };

    private MedicationTypeInterface medicationTypeInterface= new MedicationTypeInterface() {
        @Override
        public void add(String type) {
            medicationRemoveOrAddAdapter = new MedicationRemoveOrAddAdapter(PrescriptionEngineActivity.this, arrayListMedication, "D");
            rc_medication.setAdapter(medicationRemoveOrAddAdapter);
            spinnerForMedication.closeSpinerDialog();
            arrayListMedication.add(type);
            HashSet hs = new HashSet();
            hs.addAll(arrayListMedication);
            arrayListMedication.clear();
            arrayListMedication.addAll(hs);
            if (arrayListMedication.size() > 0) {
                tv_medication_for.setVisibility(View.GONE);
                rc_medication.setVisibility(View.VISIBLE);
            } else {
                rc_medication.setVisibility(View.GONE);
                tv_medication_for.setVisibility(View.VISIBLE);
            }
            medicationRemoveOrAddAdapter.notifyDataSetChanged();
        }
    };
    private void loadData() {
        progress_bar.setVisibility(View.VISIBLE);
        compositeDisposable.add(mService.getInvestigationList("PATH,RAD").observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<InvestigationListResponses>() {
            @Override
            public void accept(InvestigationListResponses investigationListResponses) throws Exception {

                for (Investigation investigation :investigationListResponses.data_list){
                    investigationArrayList.add(investigation.item_name);
                }
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
    private void loadDiagnosisData() {
        progress_bar.setVisibility(View.VISIBLE);
        compositeDisposable.add(mService.getDiagnosisList("DIAGNOSIS").observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<DiagnosisListReponses>() {
            @Override
            public void accept(DiagnosisListReponses diagnosisListReponses) throws Exception {

                for (Diagnosis diagnosis :diagnosisListReponses.data_list){
                    diagnosisArrayList.add(diagnosis.lookup_data_name);
                }
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
    private void loadMedicationData() {
        progress_bar.setVisibility(View.VISIBLE);
        compositeDisposable.add(mService.getMedicationList("MED").observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<MedicationListResponses>() {
            @Override
            public void accept(MedicationListResponses medicationListResponses) throws Exception {

                for (Medication medication :medicationListResponses.data_list){
                    medicationArrayList.add(medication.item_name);
                }
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