package com.nextgenit.doctor.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nextgenit.doctor.Adapter.AdviseRemoveOrAddAdapter;
import com.nextgenit.doctor.Adapter.DiagnosisRemoveOrAddAdapter;
import com.nextgenit.doctor.Adapter.DoseRemoveOrAddAdapter;
import com.nextgenit.doctor.Adapter.DurationRemoveOrAddAdapter;
import com.nextgenit.doctor.Adapter.InstructionRemoveOrAddAdapter;
import com.nextgenit.doctor.Adapter.InvestigationRemoveOrAddAdapter;
import com.nextgenit.doctor.Adapter.MedicationRemoveOrAddAdapter;
import com.nextgenit.doctor.Filterable.SpinnerForAdvise;
import com.nextgenit.doctor.Filterable.SpinnerForDiagnosis;
import com.nextgenit.doctor.Filterable.SpinnerForDose;
import com.nextgenit.doctor.Filterable.SpinnerForDuration;
import com.nextgenit.doctor.Filterable.SpinnerForInstruction;
import com.nextgenit.doctor.Filterable.SpinnerForInvestigation;
import com.nextgenit.doctor.Filterable.SpinnerForMedication;
import com.nextgenit.doctor.Interface.AdviceTypeInterface;
import com.nextgenit.doctor.Interface.AdviseInterface;
import com.nextgenit.doctor.Interface.DiagnosisInterface;
import com.nextgenit.doctor.Interface.DiagnosisTypeInterface;
import com.nextgenit.doctor.Interface.DoseInterface;
import com.nextgenit.doctor.Interface.DoseTypeInterface;
import com.nextgenit.doctor.Interface.DurationInterface;
import com.nextgenit.doctor.Interface.DurationTypeInterface;
import com.nextgenit.doctor.Interface.InstructionInterface;
import com.nextgenit.doctor.Interface.InstructionTypeInterface;
import com.nextgenit.doctor.Interface.InvestigationInterface;
import com.nextgenit.doctor.Interface.InvestigationTypeInterface;
import com.nextgenit.doctor.Interface.MedicationInterface;
import com.nextgenit.doctor.Interface.MedicationTypeInterface;
import com.nextgenit.doctor.LocalModel.DiagnosisData;
import com.nextgenit.doctor.LocalModel.PrescriptionModel;
import com.nextgenit.doctor.Network.IRetrofitApi;
import com.nextgenit.doctor.NetworkModel.Diagnosis;
import com.nextgenit.doctor.NetworkModel.DiagnosisListReponses;
import com.nextgenit.doctor.NetworkModel.Investigation;
import com.nextgenit.doctor.NetworkModel.InvestigationListResponses;
import com.nextgenit.doctor.NetworkModel.Medication;
import com.nextgenit.doctor.NetworkModel.MedicationListResponses;
import com.nextgenit.doctor.NetworkModel.NewPatientList;
import com.nextgenit.doctor.NetworkModel.PatientList;
import com.nextgenit.doctor.NetworkModel.Pharmacy;
import com.nextgenit.doctor.NetworkModel.PharmacyListResponses;
import com.nextgenit.doctor.R;
import com.nextgenit.doctor.Utils.Common;
import com.nextgenit.doctor.Utils.SharedPreferenceUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class PrescriptionEngineActivity extends AppCompatActivity {
    NewPatientList patientList;
    TextView tv_investigation;
    TextView tv_diagnosis;
    TextView tv_medication;
    TextView tv_dose;
    TextView tv_duration;
    TextView tv_advise;
    TextView tv_instruction;
    static TextView tv_investigation_for;
    static TextView tv_diagnosis_for;
    static TextView tv_medication_for;
    static TextView tv_dose_for;
    static TextView tv_duration_for;
    static TextView tv_advise_for;
    static TextView tv_instruction_for;
    SpinnerForInvestigation spinnerForInvestigation;
    SpinnerForDiagnosis spinnerForDiagnosis;
    SpinnerForMedication spinnerForMedication;
    SpinnerForDose spinnerForDose;
    SpinnerForDuration spinnerForDuration;
    SpinnerForInstruction spinnerForInstruction;
    SpinnerForAdvise spinnerForAdvise;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofitApi mService;
    ProgressBar progress_bar;
    ArrayList<Diagnosis> digArrayList;
    ArrayList<Diagnosis> digAdviseArrayList;
    ArrayList<Investigation> invArrayList;
    ArrayList<String> investigationArrayList = new ArrayList<>();
    ArrayList<String> diagnosisArrayList = new ArrayList<>();
    ArrayList<String> medicationArrayList = new ArrayList<>();
    ArrayList<String> doseArrayList = new ArrayList<>();
    ArrayList<String> durationArrayList = new ArrayList<>();
    ArrayList<String> instructionArrayList = new ArrayList<>();
    ArrayList<String> adviseArrayList = new ArrayList<>();
    InvestigationRemoveOrAddAdapter investigationRemoveOrAddAdapter;
    MedicationRemoveOrAddAdapter medicationRemoveOrAddAdapter;
    DiagnosisRemoveOrAddAdapter diagnosisRemoveOrAddAdapter;
    DoseRemoveOrAddAdapter doseRemoveOrAddAdapter;
    DurationRemoveOrAddAdapter durationRemoveOrAddAdapter;
    InstructionRemoveOrAddAdapter instructionRemoveOrAddAdapter;
    AdviseRemoveOrAddAdapter adviseRemoveOrAddAdapter;
    static ArrayList<String> arrayList = new ArrayList<>();
    static ArrayList<String> arrayListSecond = new ArrayList<>();
    static ArrayList<String> arrayListDignosis = new ArrayList<>();
    static ArrayList<String> arrayListDignosisSecond = new ArrayList<>();
    static ArrayList<String> arrayListMedication = new ArrayList<>();
    static ArrayList<String> arrayListMedicationSecond = new ArrayList<>();
    static ArrayList<String> arrayListDose = new ArrayList<>();
    static ArrayList<String> arrayListDuration = new ArrayList<>();
    static ArrayList<String> arrayListDurationSecond = new ArrayList<>();
    static ArrayList<String> arrayListInstructon = new ArrayList<>();
    static ArrayList<String> arrayListAdvise = new ArrayList<>();
    static RecyclerView rc_investigation;
    static RecyclerView rc_diagnosis;
    static RecyclerView rc_medication;
    static RecyclerView rc_dose;
    static RecyclerView rc_advise;
    static RecyclerView rc_duration;
    static RecyclerView rc_instruction;
    LinearLayout linear_diagnosis;
    Button btn_prescription;

    ImageView img_close;
    ImageView img_log_out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_engine);
        mService = Common.getApiXact();
        tv_instruction = findViewById(R.id.tv_instruction);
        tv_duration = findViewById(R.id.tv_duration);
        tv_advise = findViewById(R.id.tv_advise);
        tv_medication_for = findViewById(R.id.tv_medication_for);
        tv_advise_for = findViewById(R.id.tv_advise_for);
        tv_duration_for = findViewById(R.id.tv_duration_for);
        rc_dose = findViewById(R.id.rc_dose);
        rc_advise = findViewById(R.id.rc_advise);
        rc_instruction = findViewById(R.id.rc_instruction);
        rc_duration = findViewById(R.id.rc_duration);
        tv_dose = findViewById(R.id.tv_dose);
        tv_dose_for = findViewById(R.id.tv_dose_for);
        img_close = findViewById(R.id.img_close);
        tv_medication = findViewById(R.id.tv_medication);
        rc_investigation = findViewById(R.id.rc_investigation);
        tv_diagnosis_for = findViewById(R.id.tv_diagnosis_for);
        tv_diagnosis = findViewById(R.id.tv_diagnosis);
        rc_diagnosis = findViewById(R.id.rc_diagnosis);
        rc_medication = findViewById(R.id.rc_medication);
        tv_investigation_for = findViewById(R.id.tv_investigation_for);
        linear_diagnosis = findViewById(R.id.linear_diagnosis);
        btn_prescription = findViewById(R.id.btn_prescription);
        tv_instruction_for = findViewById(R.id.tv_instruction_for);
        img_log_out = findViewById(R.id.img_log_out);
        LinearLayoutManager lm1 = new LinearLayoutManager(this);
        LinearLayoutManager lm2 = new LinearLayoutManager(this);
        LinearLayoutManager lm3 = new LinearLayoutManager(this);
        LinearLayoutManager lm4 = new LinearLayoutManager(this);
        LinearLayoutManager lm5 = new LinearLayoutManager(this);
        LinearLayoutManager lm6 = new LinearLayoutManager(this);
        LinearLayoutManager lm7 = new LinearLayoutManager(this);
        lm1.setOrientation(LinearLayoutManager.VERTICAL);
        lm2.setOrientation(LinearLayoutManager.VERTICAL);
        lm3.setOrientation(LinearLayoutManager.VERTICAL);
        lm4.setOrientation(LinearLayoutManager.VERTICAL);
        lm5.setOrientation(LinearLayoutManager.VERTICAL);
        lm6.setOrientation(LinearLayoutManager.VERTICAL);
        lm7.setOrientation(LinearLayoutManager.VERTICAL);
        rc_investigation.setLayoutManager(lm1);
        rc_diagnosis.setLayoutManager(lm2);
        rc_medication.setLayoutManager(lm3);
        rc_dose.setLayoutManager(lm4);
        rc_duration.setLayoutManager(lm5);
        rc_advise.setLayoutManager(lm6);
        rc_instruction.setLayoutManager(lm7);
        tv_investigation = findViewById(R.id.tv_investigation);
        progress_bar = findViewById(R.id.progress_bar);
        patientList = getIntent().getExtras().getParcelable("patient");
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        img_log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(PrescriptionEngineActivity.this,LoginActivity.class));
                SharedPreferenceUtil.saveShared(PrescriptionEngineActivity.this, SharedPreferenceUtil.TYPE_USER_ID,  "");
                finish();
            }
        });
        tv_investigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerForInvestigation = new SpinnerForInvestigation(PrescriptionEngineActivity.this, investigationArrayList, "Select Investigation", investigationInterface, "D", investigationTypeInterfac);
                spinnerForInvestigation.showSpinerDialog();
            }
        });
        tv_diagnosis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerForDiagnosis = new SpinnerForDiagnosis(PrescriptionEngineActivity.this, diagnosisArrayList, "Select Diagnosis", diagnosisInterface, "D", diagnosisTypeInterface);
                spinnerForDiagnosis.showSpinerDialog();
            }
        });
        tv_medication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerForMedication = new SpinnerForMedication(PrescriptionEngineActivity.this, medicationArrayList, "Select Medication", medicationInterface, "D", medicationTypeInterface);
                spinnerForMedication.showSpinerDialog();
            }
        });

        tv_dose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerForDose = new SpinnerForDose(PrescriptionEngineActivity.this, doseArrayList, "Select Dose", doseInterface, "D", doseTypeInterface);
                spinnerForDose.showSpinerDialog();
            }
        });
        tv_duration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerForDuration = new SpinnerForDuration(PrescriptionEngineActivity.this, durationArrayList, "Select Duration", "D", durationTypeInterface);
                spinnerForDuration.showSpinerDialog();
            }
        });
        tv_advise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerForAdvise = new SpinnerForAdvise(PrescriptionEngineActivity.this, adviseArrayList, "Select Advise", adviseInterface, "D", adviceTypeInterface);
                spinnerForAdvise.showSpinerDialog();
            }
        });
        tv_instruction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerForInstruction = new SpinnerForInstruction(PrescriptionEngineActivity.this, instructionArrayList, "Select Instruction", instructionInterface, "D", instructionTypeInterface);
                spinnerForInstruction.showSpinerDialog();
            }
        });
        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(PrescriptionEngineActivity.this);
        progressDoalog.setMessage("Data Syncing....");
        progressDoalog.show();
        loadData();
        loadDiagnosisData();
        loadMedicationData();
        loadDoseData();
        loadInstructionData();
        loadDurationData();
        loadAdviseData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                progressDoalog.dismiss();
            }
        }, 12000);
        btn_prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (arrayListMedication.size()==arrayListDose.size() && arrayListMedication.size()==arrayListInstructon.size()){

                    Intent intent = new Intent(PrescriptionEngineActivity.this, PrescriptionViewActivity.class);
                    intent.putStringArrayListExtra("advise", arrayListAdvise);
                    intent.putStringArrayListExtra("dose", arrayListDose);
                    intent.putStringArrayListExtra("instruction", arrayListInstructon);
                    intent.putStringArrayListExtra("diagnosis", arrayListDignosis);
                    intent.putStringArrayListExtra("investigation", arrayList);
                    intent.putStringArrayListExtra("medication", arrayListMedication);
                    intent.putExtra("patient", patientList);
                    //intent.putExtra("data", showFor());
                    startActivity(intent);
                    showFor();
                }
                else{
                    Toast.makeText(PrescriptionEngineActivity.this, "Please Select Dose and Instruction", Toast.LENGTH_SHORT).show();
                }

//                arrayList.clear();
//                arrayListDignosis.clear();
//                arrayListMedication.clear();
//                arrayListDuration.clear();
//                arrayListDose.clear();
//                arrayListAdvise.clear();
//                arrayListInstructon.clear();
            }
        });
    }

    private void showFor(){
        int value = 0;
        int valueFor = 0;
        PrescriptionModel prescriptionModel = new PrescriptionModel();
        prescriptionModel.appointment_id = patientList.appointment_id;
        prescriptionModel.pharmacy_id = patientList.pharmacy_id;
        prescriptionModel.prescription_id = 0;
        prescriptionModel.prs_state = "NEW";
        prescriptionModel.created_by = Integer.parseInt(SharedPreferenceUtil.getUserID(PrescriptionEngineActivity.this));
      //  prescriptionModel.pharmacy_id = Integer.parseInt(SharedPreferenceUtil.getPharmacyId(PrescriptionEngineActivity.this));

        ArrayList<PrescriptionModel.Details> diagnosisDataArrayList = new ArrayList<>();
        ArrayList<PrescriptionModel.Medicine> medicineDataArrayList = new ArrayList<>();

        ArrayList<String> arrayListDiagnosisDuplicate =arrayListDignosis;
        ArrayList<String> arrayListInvestigation =arrayList;
        ArrayList<String> arrayListAdviseDuplicate = arrayListAdvise;

        for (Diagnosis diagnosis : digAdviseArrayList) {
            for (int i = 0; i < arrayListAdvise.size(); i++) {
                if (arrayListAdvise.get(i).equals(diagnosis.lookup_data_name)) {
                    value = value + 1;
                    PrescriptionModel.Details diagnosisData = new PrescriptionModel.Details();
                    diagnosisData.id = diagnosis.lookup_data_id;
                    diagnosisData.lookup_type = diagnosis.lookup_code;
                    diagnosisData.lookup_name = diagnosis.lookup_data_name;
                    diagnosisData.lookup_id = diagnosis.lookup_id;
                    diagnosisData.pharmacy_id = patientList.pharmacy_id;
                    diagnosisData.sl = value;
                    diagnosisDataArrayList.add(diagnosisData);
                    arrayListAdviseDuplicate.remove(i);
                }
            }

        }
        for (Diagnosis diagnosis : digArrayList) {
            for (int i = 0; i < arrayListDignosis.size(); i++) {
                if (arrayListDignosis.get(i).equals(diagnosis.lookup_data_name)) {
                    value = value + 1;
                    PrescriptionModel.Details diagnosisData = new PrescriptionModel.Details();
                    diagnosisData.id = diagnosis.lookup_data_id;
                    diagnosisData.lookup_type = diagnosis.lookup_code;
                    diagnosisData.lookup_name = diagnosis.lookup_data_name;
                    diagnosisData.lookup_id = diagnosis.lookup_id;
                    diagnosisData.pharmacy_id = patientList.pharmacy_id;
                    diagnosisData.sl = value;
                    diagnosisDataArrayList.add(diagnosisData);
                    arrayListDiagnosisDuplicate.remove(i);
                }
            }

        }
        for (Investigation investigation : invArrayList) {
            for (int i = 0; i < arrayList.size(); i++) {
                if (arrayList.get(i).equals(investigation.item_name)) {
                    value = value + 1;
                    PrescriptionModel.Details diagnosisData = new PrescriptionModel.Details();
                    diagnosisData.id = Integer.parseInt(investigation.item_id);
                    diagnosisData.lookup_type = investigation.item_type;
                    diagnosisData.lookup_name = investigation.item_name;
                    diagnosisData.lookup_id = Integer.parseInt(investigation.item_id);
                    diagnosisData.pharmacy_id = patientList.pharmacy_id;
                    diagnosisData.sl = value;
                    diagnosisDataArrayList.add(diagnosisData);
                    arrayListInvestigation.remove(i);
                }
            }
        }

        ArrayList<String> arrayListMedDuplicate =arrayListMedication;
        ArrayList<String> arrayListMedDoseDuplicate = arrayListDose;
        ArrayList<String> arrayListMedDurationDuplicate = arrayListDuration;
        ArrayList<String> arrayListMedDurationSecondDuplicate = arrayListDurationSecond;
        ArrayList<String> arrayListMedInstructionDuplicate = arrayListInstructon;

        for (Medication medication : medArraylist) {
            for (int i = 0; i < arrayListMedication.size(); i++) {

                PrescriptionModel.Medicine medicineData = new PrescriptionModel.Medicine();
                if (arrayListMedication.get(i).equals(medication.item_name)) {
                    valueFor = valueFor + 1;
                    medicineData.id = Integer.parseInt(medication.item_id);
                    medicineData.med_name = medication.item_name;
                    medicineData.med_dose = arrayListDose.get(i);
                    medicineData.med_duration = arrayListDuration.get(i);
                    medicineData.med_duration_mu = arrayListDurationSecond.get(i);
                    medicineData.med_instruction = arrayListInstructon.get(i);
                    medicineData.med_qnty = 0;
                    medicineData.sl = valueFor;
                    medicineDataArrayList.add(medicineData);
                    arrayListMedDuplicate.remove(i);
                    arrayListMedDurationDuplicate.remove(i);
                    arrayListMedDurationSecondDuplicate.remove(i);
                    arrayListMedDoseDuplicate.remove(i);
                    arrayListMedInstructionDuplicate.remove(i);
                }

            }
        }


        for (int i = 0; i < arrayListMedDuplicate.size(); i++) {
            valueFor = valueFor + 1;
            PrescriptionModel.Medicine medicineData = new PrescriptionModel.Medicine();
            medicineData.id = 0;
            medicineData.med_name = arrayListMedDuplicate.get(i);
            medicineData.med_dose = arrayListMedDoseDuplicate.get(i);
            medicineData.med_duration = arrayListMedDurationDuplicate.get(i);
            medicineData.med_duration_mu = arrayListMedDurationSecondDuplicate.get(i);
            medicineData.med_instruction = arrayListMedInstructionDuplicate.get(i);
            medicineData.med_qnty = 0;
            medicineData.sl = valueFor;
            medicineDataArrayList.add(medicineData);
        }

///


        for (int i = 0; i < arrayListDiagnosisDuplicate.size(); i++) {
            value = value + 1;
            PrescriptionModel.Details diagnosisData = new PrescriptionModel.Details();
            diagnosisData.id =0;
            diagnosisData.lookup_type = "DIAGNOSIS";
            diagnosisData.lookup_name = arrayListDiagnosisDuplicate.get(i);
            diagnosisData.lookup_id = 0;
            diagnosisData.pharmacy_id = patientList.pharmacy_id;
            diagnosisData.sl = value;
            diagnosisDataArrayList.add(diagnosisData);
        }

        for (int i = 0; i < arrayListInvestigation.size(); i++) {
            value = value + 1;
            PrescriptionModel.Details diagnosisData = new PrescriptionModel.Details();
            diagnosisData.id =0;
            diagnosisData.lookup_type = "PATH";
            diagnosisData.lookup_name = arrayListInvestigation.get(i);
            diagnosisData.lookup_id = 0;
            diagnosisData.pharmacy_id = patientList.pharmacy_id;
            diagnosisData.sl = value;
            diagnosisDataArrayList.add(diagnosisData);
        }
        for (int i = 0; i < arrayListAdviseDuplicate.size(); i++) {
            value = value + 1;
            PrescriptionModel.Details diagnosisData = new PrescriptionModel.Details();
            diagnosisData.id =0;
            diagnosisData.lookup_type = "ADVICE";
            diagnosisData.lookup_name = arrayListAdviseDuplicate.get(i);
            diagnosisData.lookup_id = 0;
            diagnosisData.pharmacy_id = patientList.pharmacy_id;
            diagnosisData.sl = value;
            diagnosisDataArrayList.add(diagnosisData);
        }


        //
        prescriptionModel.details_data = diagnosisDataArrayList;
        prescriptionModel.medicine_data = medicineDataArrayList;
        String s = new Gson().toJson(prescriptionModel);
        SharedPreferenceUtil.saveShared(PrescriptionEngineActivity.this, SharedPreferenceUtil.DATA, s + "");


    }
    public  ArrayList<String> removeDuplicates(ArrayList<String> list)
    {

        // Create a new ArrayList
        ArrayList<String> newList = new ArrayList<String>();

        // Traverse through the first list
        for (String element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    public String method(String str) {
        if (str != null && str.length() > 0 && str.charAt(str.length() - 1) == 'x') {
            str = str.substring(0, str.length() - 1);
        }
        return str;
    }
    private  String removeLastChar(String str) {
        return str.substring( 0,str.length() - 1);
    }
    private  String getLastChar(String str) {
        return str.substring( str.length() - 1);
    }
    private InvestigationInterface investigationInterface = new InvestigationInterface() {
        @Override
        public void postion(String position, String Type) {
            investigationRemoveOrAddAdapter = new InvestigationRemoveOrAddAdapter(PrescriptionEngineActivity.this, arrayList, "D");
            rc_investigation.setAdapter(investigationRemoveOrAddAdapter);
            spinnerForInvestigation.closeSpinerDialog();
            arrayList.add(position);
            arrayListSecond.add(position+"%");
//            HashSet hs = new HashSet();
//            hs.addAll(arrayList);
//            arrayList.clear();
//            arrayList.addAll(hs);
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


    private DiagnosisInterface diagnosisInterface = new DiagnosisInterface() {
        @Override
        public void postion(String position, String Type) {
            diagnosisRemoveOrAddAdapter = new DiagnosisRemoveOrAddAdapter(PrescriptionEngineActivity.this, arrayListDignosis, "D");
            rc_diagnosis.setAdapter(diagnosisRemoveOrAddAdapter);
            spinnerForDiagnosis.closeSpinerDialog();
            arrayListDignosis.add(position);
            arrayListDignosisSecond.add(position+"%");

//            HashSet hs = new HashSet();
//            hs.addAll(arrayListDignosis);
//            arrayListDignosis.clear();
//            arrayListDignosis.addAll(hs);
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
    private DiagnosisTypeInterface diagnosisTypeInterface = new DiagnosisTypeInterface() {
        @Override
        public void add(String type) {
            diagnosisRemoveOrAddAdapter = new DiagnosisRemoveOrAddAdapter(PrescriptionEngineActivity.this, arrayListDignosis, "D");
            rc_diagnosis.setAdapter(diagnosisRemoveOrAddAdapter);
            spinnerForDiagnosis.closeSpinerDialog();
            arrayListDignosis.add(type);
            arrayListDignosisSecond.add(type);
//            HashSet hs = new HashSet();
//            hs.addAll(arrayListDignosis);
//            arrayListDignosis.clear();
//            arrayListDignosis.addAll(hs);
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

    public static void DoseShow() {
        if (arrayListDose.size() > 0) {
            tv_dose_for.setVisibility(View.GONE);
            rc_dose.setVisibility(View.VISIBLE);
        } else {
            rc_dose.setVisibility(View.GONE);
            tv_dose_for.setVisibility(View.VISIBLE);
        }

    }

    public static void DurationShow() {
        if (arrayListDuration.size() > 0) {
            tv_duration_for.setVisibility(View.GONE);
            rc_duration.setVisibility(View.VISIBLE);
        } else {
            rc_duration.setVisibility(View.GONE);
            tv_duration_for.setVisibility(View.VISIBLE);
        }

    }

    public static void InstructionShow() {
        if (arrayListInstructon.size() > 0) {
            tv_instruction_for.setVisibility(View.GONE);
            rc_instruction.setVisibility(View.VISIBLE);
        } else {
            rc_instruction.setVisibility(View.GONE);
            tv_instruction_for.setVisibility(View.VISIBLE);
        }

    }

    public static void AdviceShow() {
        if (arrayListAdvise.size() > 0) {
            tv_advise_for.setVisibility(View.GONE);
            rc_advise.setVisibility(View.VISIBLE);
        } else {
            rc_advise.setVisibility(View.GONE);
            tv_advise_for.setVisibility(View.VISIBLE);
        }

    }

    InvestigationTypeInterface investigationTypeInterfac = new InvestigationTypeInterface() {
        @Override
        public void add(String type) {
            investigationRemoveOrAddAdapter = new InvestigationRemoveOrAddAdapter(PrescriptionEngineActivity.this, arrayList, "D");
            rc_investigation.setAdapter(investigationRemoveOrAddAdapter);
            spinnerForInvestigation.closeSpinerDialog();
            arrayList.add(type);
            arrayListSecond.add(type);
//            HashSet hs = new HashSet();
//            hs.addAll(arrayList);
//            arrayList.clear();
//            arrayList.addAll(hs);
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
    private MedicationInterface medicationInterface = new MedicationInterface() {
        @Override
        public void postion(String position, String Type) {
            medicationRemoveOrAddAdapter = new MedicationRemoveOrAddAdapter(PrescriptionEngineActivity.this, arrayListMedication, "D");
            rc_medication.setAdapter(medicationRemoveOrAddAdapter);
            spinnerForMedication.closeSpinerDialog();
            arrayListMedication.add(position);
            arrayListMedicationSecond.add(position+"%");
//            HashSet hs = new HashSet();
//            HashSet hs1 = new HashSet();
//            hs.addAll(arrayListMedication);
//            hs1.addAll(arrayListMedicationSecond);
//            arrayListMedication.clear();
//            arrayListMedicationSecond.clear();
//            arrayListMedication.addAll(hs);
//            arrayListMedicationSecond.addAll(hs1);
            if (arrayListMedication.size() > 0) {
                tv_medication_for.setVisibility(View.GONE);
                rc_medication.setVisibility(View.VISIBLE);
            } else {
                rc_medication.setVisibility(View.GONE);
                tv_medication_for.setVisibility(View.VISIBLE);
            }
            medicationRemoveOrAddAdapter.notifyDataSetChanged();
            onDoseShow();
        }
    };

    private MedicationTypeInterface medicationTypeInterface = new MedicationTypeInterface() {
        @Override
        public void add(String type) {

            medicationRemoveOrAddAdapter = new MedicationRemoveOrAddAdapter(PrescriptionEngineActivity.this, arrayListMedication, "D");
            rc_medication.setAdapter(medicationRemoveOrAddAdapter);
            spinnerForMedication.closeSpinerDialog();
            arrayListMedication.add(type);
            arrayListMedicationSecond.add(type);
//            HashSet hs = new HashSet();
//            HashSet hs1 = new HashSet();
//            hs.addAll(arrayListMedication);
//            hs1.addAll(arrayListMedicationSecond);
//            arrayListMedicationSecond.clear();
//            arrayListMedication.clear();
//            arrayListMedication.addAll(hs);
//            arrayListMedicationSecond.addAll(hs1);
            if (arrayListMedication.size() > 0) {
                tv_medication_for.setVisibility(View.GONE);
                rc_medication.setVisibility(View.VISIBLE);
            } else {
                rc_medication.setVisibility(View.GONE);
                tv_medication_for.setVisibility(View.VISIBLE);
            }
            medicationRemoveOrAddAdapter.notifyDataSetChanged();
            onDoseShow();

        }
    };

    private void onDoseShow() {
        spinnerForDose = new SpinnerForDose(PrescriptionEngineActivity.this, doseArrayList, "Select Dose", doseInterface, "D", doseTypeInterface);
        spinnerForDose.showSpinerDialog();
    }

    private void onDurationShow() {

        spinnerForDuration = new SpinnerForDuration(PrescriptionEngineActivity.this, durationArrayList, "Select Duration", "D", durationTypeInterface);
        spinnerForDuration.showSpinerDialog();


    }

    private void onInstructionShow() {

        spinnerForInstruction = new SpinnerForInstruction(PrescriptionEngineActivity.this, instructionArrayList, "Select Instruction", instructionInterface, "D", instructionTypeInterface);
        spinnerForInstruction.showSpinerDialog();


    }

    private void onAdviseShow() {
        spinnerForAdvise = new SpinnerForAdvise(PrescriptionEngineActivity.this, adviseArrayList, "Select Advise", adviseInterface, "D", adviceTypeInterface);
        spinnerForAdvise.showSpinerDialog();
    }
    ////Instruction//////


    private InstructionInterface instructionInterface = new InstructionInterface() {
        @Override
        public void postion(String position, String Type) {
            instructionRemoveOrAddAdapter = new InstructionRemoveOrAddAdapter(PrescriptionEngineActivity.this, arrayListInstructon, "D");
            rc_instruction.setAdapter(instructionRemoveOrAddAdapter);
            spinnerForInstruction.closeSpinerDialog();
            arrayListInstructon.add(position);
            if (arrayListInstructon.size() > 0) {
                tv_instruction_for.setVisibility(View.GONE);
                rc_instruction.setVisibility(View.VISIBLE);
            } else {
                rc_instruction.setVisibility(View.GONE);
                tv_instruction_for.setVisibility(View.VISIBLE);
            }
            instructionRemoveOrAddAdapter.notifyDataSetChanged();
            onAdviseShow();
        }
    };

    private InstructionTypeInterface instructionTypeInterface = new InstructionTypeInterface() {
        @Override
        public void add(String type) {
            instructionRemoveOrAddAdapter = new InstructionRemoveOrAddAdapter(PrescriptionEngineActivity.this, arrayListInstructon, "D");
            rc_instruction.setAdapter(instructionRemoveOrAddAdapter);
            spinnerForInstruction.closeSpinerDialog();
            arrayListInstructon.add(type);
//            HashSet hs = new HashSet();
//            hs.addAll(arrayListDose);
//            arrayListDose.clear();
//            arrayListDose.addAll(hs);
            if (arrayListInstructon.size() > 0) {
                tv_instruction_for.setVisibility(View.GONE);
                rc_instruction.setVisibility(View.VISIBLE);
            } else {
                rc_instruction.setVisibility(View.GONE);
                tv_instruction_for.setVisibility(View.VISIBLE);
            }
            instructionRemoveOrAddAdapter.notifyDataSetChanged();
            onAdviseShow();
        }
    };
    ///////Dose////////////
    private DoseInterface doseInterface = new DoseInterface() {
        @Override
        public void postion(String position, String Type) {
            doseRemoveOrAddAdapter = new DoseRemoveOrAddAdapter(PrescriptionEngineActivity.this, arrayListDose, "D");
            rc_dose.setAdapter(doseRemoveOrAddAdapter);
            spinnerForDose.closeSpinerDialog();
            arrayListDose.add(position);
//            HashSet hs = new HashSet();
//            hs.addAll(arrayListDose);
//            arrayListDose.clear();
//            arrayListDose.addAll(hs);
            if (arrayListDose.size() > 0) {
                tv_dose_for.setVisibility(View.GONE);
                rc_dose.setVisibility(View.VISIBLE);
            } else {
                rc_dose.setVisibility(View.GONE);
                tv_dose_for.setVisibility(View.VISIBLE);
            }
            doseRemoveOrAddAdapter.notifyDataSetChanged();
            onDurationShow();
        }
    };

    private DoseTypeInterface doseTypeInterface = new DoseTypeInterface() {
        @Override
        public void add(String type) {
            doseRemoveOrAddAdapter = new DoseRemoveOrAddAdapter(PrescriptionEngineActivity.this, arrayListDose, "D");
            rc_dose.setAdapter(doseRemoveOrAddAdapter);
            spinnerForDose.closeSpinerDialog();
            arrayListDose.add(type);
//            HashSet hs = new HashSet();
//            hs.addAll(arrayListDose);
//            arrayListDose.clear();
//            arrayListDose.addAll(hs);
            if (arrayListDose.size() > 0) {
                tv_dose_for.setVisibility(View.GONE);
                rc_dose.setVisibility(View.VISIBLE);
            } else {
                rc_dose.setVisibility(View.GONE);
                tv_dose_for.setVisibility(View.VISIBLE);
            }
            doseRemoveOrAddAdapter.notifyDataSetChanged();
            onDurationShow();
        }
    };


    ///////////Dose/////
    ///////Duration////////////
    private DurationInterface durationInterface = new DurationInterface() {
        @Override
        public void postion(String position, String Type) {
            durationRemoveOrAddAdapter = new DurationRemoveOrAddAdapter(PrescriptionEngineActivity.this, arrayListDuration, "D");
            rc_duration.setAdapter(durationRemoveOrAddAdapter);
            spinnerForDuration.closeSpinerDialog();
            arrayListDuration.add(position);
//            HashSet hs = new HashSet();
//            hs.addAll(arrayListDuration);
//            arrayListDuration.clear();
//            arrayListDuration.addAll(hs);
            if (arrayListDuration.size() > 0) {
                tv_duration_for.setVisibility(View.GONE);
                rc_duration.setVisibility(View.VISIBLE);
            } else {
                rc_duration.setVisibility(View.GONE);
                tv_duration_for.setVisibility(View.VISIBLE);
            }
            durationRemoveOrAddAdapter.notifyDataSetChanged();
            onInstructionShow();
        }
    };

    private DurationTypeInterface durationTypeInterface = new DurationTypeInterface() {
        @Override
        public void add(String type,String Duration) {
            durationRemoveOrAddAdapter = new DurationRemoveOrAddAdapter(PrescriptionEngineActivity.this, arrayListDuration, "D");
            rc_duration.setAdapter(durationRemoveOrAddAdapter);
            spinnerForDuration.closeSpinerDialog();
            arrayListDuration.add(type);
            arrayListDurationSecond.add(Duration);
//            HashSet hs = new HashSet();
//            hs.addAll(arrayListDuration);
//            arrayListDuration.clear();
//            arrayListDuration.addAll(hs);
            if (arrayListDuration.size() > 0) {
                tv_duration_for.setVisibility(View.GONE);
                rc_duration.setVisibility(View.VISIBLE);
            } else {
                rc_duration.setVisibility(View.GONE);
                tv_duration_for.setVisibility(View.VISIBLE);
            }
            durationRemoveOrAddAdapter.notifyDataSetChanged();
            onInstructionShow();
        }
    };


    ///////////Duration/////
    ///////Advise////////////
    private AdviseInterface adviseInterface = new AdviseInterface() {
        @Override
        public void postion(String position, String Type) {
            adviseRemoveOrAddAdapter = new AdviseRemoveOrAddAdapter(PrescriptionEngineActivity.this, arrayListAdvise, "D");
            rc_advise.setAdapter(adviseRemoveOrAddAdapter);
            spinnerForAdvise.closeSpinerDialog();
            arrayListAdvise.add(position);
//            HashSet hs = new HashSet();
//            hs.addAll(arrayListAdvise);
//            arrayListAdvise.clear();
//            arrayListAdvise.addAll(hs);
            if (arrayListAdvise.size() > 0) {
                tv_advise_for.setVisibility(View.GONE);
                rc_advise.setVisibility(View.VISIBLE);
            } else {
                rc_advise.setVisibility(View.GONE);
                tv_advise_for.setVisibility(View.VISIBLE);
            }
            adviseRemoveOrAddAdapter.notifyDataSetChanged();
        }
    };

    private AdviceTypeInterface adviceTypeInterface = new AdviceTypeInterface() {
        @Override
        public void add(String type) {
            adviseRemoveOrAddAdapter = new AdviseRemoveOrAddAdapter(PrescriptionEngineActivity.this, arrayListAdvise, "D");
            rc_advise.setAdapter(adviseRemoveOrAddAdapter);
            spinnerForAdvise.closeSpinerDialog();
            arrayListAdvise.add(type);
//            HashSet hs = new HashSet();
//            hs.addAll(arrayListAdvise);
//            arrayListAdvise.clear();
//            arrayListAdvise.addAll(hs);
            if (arrayListDuration.size() > 0) {
                tv_advise_for.setVisibility(View.GONE);
                rc_advise.setVisibility(View.VISIBLE);
            } else {
                rc_advise.setVisibility(View.GONE);
                tv_advise_for.setVisibility(View.VISIBLE);
            }
            adviseRemoveOrAddAdapter.notifyDataSetChanged();
        }
    };

    private void loadData() {

        compositeDisposable.add(mService.getInvestigationList("PATH,RAD").observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<InvestigationListResponses>() {
            @Override
            public void accept(InvestigationListResponses investigationListResponses) throws Exception {
                invArrayList = investigationListResponses.data_list;
                for (Investigation investigation : investigationListResponses.data_list) {
                    investigationArrayList.add(investigation.item_name);
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("study", "study" + throwable.getMessage());

            }
        }));
    }


    private void loadDiagnosisData() {

        compositeDisposable.add(mService.getDiagnosisList("DIAGNOSIS").observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<DiagnosisListReponses>() {
            @Override
            public void accept(DiagnosisListReponses diagnosisListReponses) throws Exception {
                digArrayList = diagnosisListReponses.data_list;
                for (Diagnosis diagnosis : diagnosisListReponses.data_list) {
                    diagnosisArrayList.add(diagnosis.lookup_data_name);
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("study", "study" + throwable.getMessage());

            }
        }));
    }

    private void loadDoseData() {

        compositeDisposable.add(mService.getDiagnosisList("DOSE").observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<DiagnosisListReponses>() {
            @Override
            public void accept(DiagnosisListReponses diagnosisListReponses) throws Exception {

                for (Diagnosis diagnosis : diagnosisListReponses.data_list) {
                    doseArrayList.add(diagnosis.lookup_data_name);
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("study", "study" + throwable.getMessage());

            }
        }));
    }

    private void loadInstructionData() {

        compositeDisposable.add(mService.getDiagnosisList("INSTRUCTION").observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<DiagnosisListReponses>() {
            @Override
            public void accept(DiagnosisListReponses diagnosisListReponses) throws Exception {

                for (Diagnosis diagnosis : diagnosisListReponses.data_list) {
                    instructionArrayList.add(diagnosis.lookup_data_name);
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("study", "study" + throwable.getMessage());

            }
        }));
    }

    private void loadDurationData() {

        compositeDisposable.add(mService.getDiagnosisList("DURMU").observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<DiagnosisListReponses>() {
            @Override
            public void accept(DiagnosisListReponses diagnosisListReponses) throws Exception {

                for (Diagnosis diagnosis : diagnosisListReponses.data_list) {
                    durationArrayList.add(diagnosis.lookup_data_name);
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("study", "study" + throwable.getMessage());

            }
        }));
    }

    private void loadAdviseData() {

        compositeDisposable.add(mService.getDiagnosisList("ADVICE").observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<DiagnosisListReponses>() {
            @Override
            public void accept(DiagnosisListReponses diagnosisListReponses) throws Exception {
                digAdviseArrayList=diagnosisListReponses.data_list;
                for (Diagnosis diagnosis : diagnosisListReponses.data_list) {
                    adviseArrayList.add(diagnosis.lookup_data_name);
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("study", "study" + throwable.getMessage());

            }
        }));
    }

    ArrayList<Medication> medArraylist;

    private void loadMedicationData() {

        compositeDisposable.add(mService.getMedicationList("MED").observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<MedicationListResponses>() {
            @Override
            public void accept(MedicationListResponses medicationListResponses) throws Exception {

                medArraylist = medicationListResponses.data_list;
                for (Medication medication : medicationListResponses.data_list) {
                    medicationArrayList.add(medication.item_name);
                }

            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e("study", "study" + throwable.getMessage());

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        arrayList.clear();
        arrayListDignosis.clear();
        arrayListMedication.clear();
        arrayListDuration.clear();
        arrayListDose.clear();
        arrayListAdvise.clear();
    }
    public static void onClear(){
        arrayList.clear();
        arrayListDignosis.clear();
        arrayListMedication.clear();
        arrayListDuration.clear();
        arrayListDose.clear();
        arrayListAdvise.clear();
    }
}