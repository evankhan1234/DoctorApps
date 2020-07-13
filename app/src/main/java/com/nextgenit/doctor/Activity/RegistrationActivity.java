package com.nextgenit.doctor.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nextgenit.doctor.Network.IRetrofitApi;
import com.nextgenit.doctor.NetworkModel.RegistrationResponses;
import com.nextgenit.doctor.NetworkModel.Specialist;
import com.nextgenit.doctor.NetworkModel.SpecialistResponses;
import com.nextgenit.doctor.R;
import com.nextgenit.doctor.Utils.Common;
import com.nextgenit.doctor.Utils.Util;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class RegistrationActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    IRetrofitApi mService;
    ProgressBar progress_bar;
    ArrayList<Specialist> data_list;
    ArrayAdapter<Specialist> specialistArrayAdapter;
    Spinner spinner_specialization;
    ImageView show_pass;
    ImageView show_confirm_pass;
    EditText et_password;
    EditText et_confirm_password;
    EditText et_name;
    EditText et_email;
    EditText et_phone;
    Button btn_registration;
    TextView tv_login;
    RelativeLayout rlt_root;
    ImageButton btn_header_back_;
    boolean test = true;
    boolean test_confirm = true;
    int specializationId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mService= Common.getApiXact();
        progress_bar=findViewById(R.id.progress_bar);
        spinner_specialization=findViewById(R.id.spinner_specialization);
        tv_login=findViewById(R.id.tv_login);
        btn_header_back_=findViewById(R.id.btn_header_back_);
        et_name=findViewById(R.id.et_name);
        btn_registration=findViewById(R.id.btn_registration);
        show_pass=findViewById(R.id.show_pass);
        show_confirm_pass=findViewById(R.id.show_confirm_pass);
        et_password=findViewById(R.id.et_password);
        et_confirm_password=findViewById(R.id.et_confirm_password);
        progress_bar=findViewById(R.id.progress_bar);
        et_email=findViewById(R.id.et_email);
        et_phone=findViewById(R.id.et_phone);
        rlt_root=findViewById(R.id.rlt_root);
        btn_header_back_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        et_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                et_email.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule));
                et_password.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule_gray));
                et_name.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule_gray));
                et_confirm_password.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule_gray));
                et_phone.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule_gray));

                et_email.setTextColor(getResources().getColor(R.color.colorPrimary));
                et_email.setHintTextColor(getResources().getColor(R.color.colorPrimary));
                et_password.setTextColor(getResources().getColor(R.color.gray_for));
                et_password.setHintTextColor(getResources().getColor(R.color.gray_for));
                et_name.setTextColor(getResources().getColor(R.color.gray_for));
                et_name.setHintTextColor(getResources().getColor(R.color.gray_for));
                et_phone.setTextColor(getResources().getColor(R.color.gray_for));
                et_phone.setHintTextColor(getResources().getColor(R.color.gray_for));
                et_confirm_password.setTextColor(getResources().getColor(R.color.gray_for));
                et_confirm_password.setHintTextColor(getResources().getColor(R.color.gray_for));
            }
        });

        et_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                et_name.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule));
                et_password.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule_gray));
                et_email.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule_gray));
                et_confirm_password.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule_gray));
                et_phone.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule_gray));

                et_name.setTextColor(getResources().getColor(R.color.colorPrimary));
                et_name.setHintTextColor(getResources().getColor(R.color.colorPrimary));
                et_password.setTextColor(getResources().getColor(R.color.gray_for));
                et_password.setHintTextColor(getResources().getColor(R.color.gray_for));
                et_email.setTextColor(getResources().getColor(R.color.gray_for));
                et_email.setHintTextColor(getResources().getColor(R.color.gray_for));
                et_phone.setTextColor(getResources().getColor(R.color.gray_for));
                et_phone.setHintTextColor(getResources().getColor(R.color.gray_for));
                et_confirm_password.setTextColor(getResources().getColor(R.color.gray_for));
                et_confirm_password.setHintTextColor(getResources().getColor(R.color.gray_for));
            }
        });
        et_phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                et_phone.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule));
                et_password.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule_gray));
                et_email.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule_gray));
                et_confirm_password.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule_gray));
                et_name.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule_gray));

                et_phone.setTextColor(getResources().getColor(R.color.colorPrimary));
                et_phone.setHintTextColor(getResources().getColor(R.color.colorPrimary));
                et_password.setTextColor(getResources().getColor(R.color.gray_for));
                et_password.setHintTextColor(getResources().getColor(R.color.gray_for));
                et_email.setTextColor(getResources().getColor(R.color.gray_for));
                et_email.setHintTextColor(getResources().getColor(R.color.gray_for));
                et_name.setTextColor(getResources().getColor(R.color.gray_for));
                et_name.setHintTextColor(getResources().getColor(R.color.gray_for));
                et_confirm_password.setTextColor(getResources().getColor(R.color.gray_for));
                et_confirm_password.setHintTextColor(getResources().getColor(R.color.gray_for));
            }
        });

        et_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                et_password.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule));
                et_phone.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule_gray));
                et_email.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule_gray));
                et_confirm_password.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule_gray));
                et_name.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule_gray));

                et_password.setTextColor(getResources().getColor(R.color.colorPrimary));
                et_password.setHintTextColor(getResources().getColor(R.color.colorPrimary));
                et_phone.setTextColor(getResources().getColor(R.color.gray_for));
                et_phone.setHintTextColor(getResources().getColor(R.color.gray_for));
                et_email.setTextColor(getResources().getColor(R.color.gray_for));
                et_email.setHintTextColor(getResources().getColor(R.color.gray_for));
                et_name.setTextColor(getResources().getColor(R.color.gray_for));
                et_name.setHintTextColor(getResources().getColor(R.color.gray_for));
                et_confirm_password.setTextColor(getResources().getColor(R.color.gray_for));
                et_confirm_password.setHintTextColor(getResources().getColor(R.color.gray_for));
            }
        });
        et_confirm_password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                et_confirm_password.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule));
                et_phone.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule_gray));
                et_email.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule_gray));
                et_password.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule_gray));
                et_name.setBackground(getResources().getDrawable(R.drawable.edit_text_capsule_gray));

                et_confirm_password.setTextColor(getResources().getColor(R.color.colorPrimary));
                et_confirm_password.setHintTextColor(getResources().getColor(R.color.colorPrimary));
                et_phone.setTextColor(getResources().getColor(R.color.gray_for));
                et_phone.setHintTextColor(getResources().getColor(R.color.gray_for));
                et_email.setTextColor(getResources().getColor(R.color.gray_for));
                et_email.setHintTextColor(getResources().getColor(R.color.gray_for));
                et_name.setTextColor(getResources().getColor(R.color.gray_for));
                et_name.setHintTextColor(getResources().getColor(R.color.gray_for));
                et_password.setTextColor(getResources().getColor(R.color.gray_for));
                et_password.setHintTextColor(getResources().getColor(R.color.gray_for));
            }
        });
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                finish();
            }
        });
        show_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int cursorPosition = et_password.getSelectionStart();

                if (test) {
                    Log.e("show", "show");

                    test = false;
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    show_pass.setImageDrawable(getResources().getDrawable(R.drawable.hidden_password));

                } else {
                    Log.e("hidden", "hidden");
                    show_pass.setImageDrawable(getResources().getDrawable(R.drawable.show_password));
                    test = true;
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                et_password.setSelection(cursorPosition);
            }
        });
        show_confirm_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int cursorPosition = et_confirm_password.getSelectionStart();

                if (test_confirm) {
                    Log.e("show", "show");

                    test_confirm = false;
                    et_confirm_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    show_pass.setImageDrawable(getResources().getDrawable(R.drawable.hidden_password));

                } else {
                    Log.e("hidden", "hidden");
                    show_pass.setImageDrawable(getResources().getDrawable(R.drawable.show_password));
                    test_confirm = true;
                    et_confirm_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                et_confirm_password.setSelection(cursorPosition);
            }
        });
        spinner_specialization.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("sp_water", "" + data_list.get(position).specialization_id);
                specializationId = data_list.get(position).specialization_id;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        btn_registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=et_name.getText().toString();
                String email=et_email.getText().toString();
                String phone=et_phone.getText().toString();
                String password=et_password.getText().toString();
                String confirm_password=et_confirm_password.getText().toString();
                if (name.equals("") && password.equals("") && email.equals("") && phone.equals("") && confirm_password.equals("") ){

                    Util.snackBar("All Data Needed",rlt_root);
                }
                else if (email.equals("") ){
                    Util.snackBar("Email is Empty",rlt_root);
                }
                else if (phone.equals("") ){
                    Util.snackBar("Phone is Empty",rlt_root);
                }
                else if (password.equals("") ){
                    Util.snackBar("Password is Empty",rlt_root);
                }
                else if (confirm_password.equals("") ){
                    Util.snackBar("Confirm Password is Empty",rlt_root);
                }
                else{
                    progress_bar.setVisibility(View.VISIBLE);
                    compositeDisposable.add(mService.postRegistration(name,email,phone,password,confirm_password,specializationId).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<RegistrationResponses>() {
                        @Override
                        public void accept(RegistrationResponses registrationResponses) throws Exception {
                            Log.e("ff", "dgg" + new Gson().toJson(registrationResponses));

                            progress_bar.setVisibility(View.GONE);
                            if (registrationResponses.status.equals("success")) {
                                //    SharedPreferenceUtil.saveShared(RegistrationActivity.this, SharedPreferenceUtil.TYPE_USER_ID, registrationResponses.user.user_no_pk + "");
                                Toast.makeText(RegistrationActivity.this, "Successfully Registration", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
                                finish();

                            }
                            else if (registrationResponses.status.equals("failed")){
                                Toast.makeText(RegistrationActivity.this, registrationResponses.message, Toast.LENGTH_SHORT).show();

                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            progress_bar.setVisibility(View.GONE);
                            Log.e("ff", "dgg" + throwable.getMessage());
                            Toast.makeText(RegistrationActivity.this, "Unauthorized", Toast.LENGTH_SHORT).show();

                        }
                    }));
                }


            }
        });
    }
    private void loadData() {
        progress_bar.setVisibility(View.VISIBLE);
        compositeDisposable.add(mService.getSpecialList().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(new Consumer<SpecialistResponses>() {
            @Override
            public void accept(SpecialistResponses specialistResponses) throws Exception {
                data_list=specialistResponses.data_list;
                specialistArrayAdapter = new ArrayAdapter<>(RegistrationActivity.this, android.R.layout.simple_spinner_item, specialistResponses.data_list);
                specialistArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_specialization.setAdapter(specialistArrayAdapter);
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