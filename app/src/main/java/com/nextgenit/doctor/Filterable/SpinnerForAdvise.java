package com.nextgenit.doctor.Filterable;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nextgenit.doctor.Adapter.AdviseAdapter;
import com.nextgenit.doctor.Adapter.DoseAdapter;
import com.nextgenit.doctor.Interface.AdviceTypeInterface;
import com.nextgenit.doctor.Interface.AdviseInterface;
import com.nextgenit.doctor.Interface.DoseInterface;
import com.nextgenit.doctor.Interface.DoseTypeInterface;
import com.nextgenit.doctor.R;

import java.util.List;

public class SpinnerForAdvise implements Filterable {
    List<String> items;
    Activity context;
    String dTitle, closeTitle = "Close";
    AlertDialog alertDialog;
    int pos;
    int style;
    boolean cancellable = false;
    boolean showKeyboard = false;
    SpinnerAdvise filter;
    ArrayAdapter adapter;
    AdviseAdapter mAdapters;
    String values;
    String types;
    AdviseInterface uccMemberClickListener;
    AdviceTypeInterface investigationTypeInterface;

    public SpinnerForAdvise(Activity activity, List<String> items, String dialogTitle, AdviseInterface uccMemberClickListeners,String type,AdviceTypeInterface investigationTypeInterfaces) {
        this.items = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.types = type;
        this.uccMemberClickListener = uccMemberClickListeners;
        this.investigationTypeInterface = investigationTypeInterfaces;
    }


    public void showSpinerDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(context);
        View v = context.getLayoutInflater().inflate(R.layout.layout_spinner, null);


        TextView rippleViewClose =  v.findViewById(R.id.close);
        TextView rippleViewAdd =  v.findViewById(R.id.add);
        TextView title =  v.findViewById(R.id.spinerTitle);
        rippleViewClose.setText(closeTitle);
        title.setText(dTitle);
        // final ListView listView = (ListView) v.findViewById(R.id.list);
        final EditText searchBox = (EditText) v.findViewById(R.id.searchBox);
        if (isShowKeyboard()) {
            showKeyboard(searchBox);
        }
        searchBox.requestFocus();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(searchBox, InputMethodManager.SHOW_IMPLICIT);
        adapter = new ArrayAdapter<>(context, R.layout.layout_investigation_for_item, items);
        RecyclerView rcl_this_customer_list = v.findViewById(R.id.rcl_this_customer_list);

        LinearLayoutManager lm = new LinearLayoutManager(context);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        rcl_this_customer_list.setLayoutManager(lm);
        mAdapters = new AdviseAdapter(context, items, uccMemberClickListener,types);

        rcl_this_customer_list.setAdapter(mAdapters);

        // listView.setAdapter(adapter);
        adb.setView(v);
        alertDialog = adb.create();
        alertDialog.getWindow().getAttributes().windowAnimations = style;//R.style.DialogAnimations_SmileWindow;


        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String s = searchBox.getText().toString();

                mAdapters.getFilter().filter(searchBox.getText().toString());
            }
        });

        rippleViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeSpinerDialog();
            }
        });
        rippleViewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = searchBox.getText().toString();
                if (!s.equals("")){
                    investigationTypeInterface.add(s);
                }

            }
        });
        try {
            alertDialog.setCancelable(isCancellable());
            alertDialog.setCanceledOnTouchOutside(isCancellable());
            alertDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void closeSpinerDialog() {
        hideKeyboard();
        if (alertDialog != null) {
            alertDialog.dismiss();
        }
    }

    private void hideKeyboard() {
        try {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
        }
    }

    private void showKeyboard(final EditText ettext) {
        ettext.requestFocus();
        ettext.postDelayed(new Runnable() {
                               @Override
                               public void run() {
                                   InputMethodManager keyboard = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                                   keyboard.showSoftInput(ettext, 0);
                               }
                           }
                , 200);
    }

    private boolean isCancellable() {
        return cancellable;
    }


    private boolean isShowKeyboard() {
        return showKeyboard;
    }



    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new SpinnerAdvise(items, mAdapters);
            Toast.makeText(context, "xvx", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "xvx", Toast.LENGTH_SHORT).show();
        }
        return filter;
    }
}

