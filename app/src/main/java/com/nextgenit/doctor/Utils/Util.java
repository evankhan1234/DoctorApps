package com.nextgenit.doctor.Utils;

import android.widget.RelativeLayout;

import com.google.android.material.snackbar.Snackbar;

public class Util {
    public static void snackBar(String message, RelativeLayout relativeLayout){
        Snackbar snackbar = Snackbar
                .make(relativeLayout, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }
}
