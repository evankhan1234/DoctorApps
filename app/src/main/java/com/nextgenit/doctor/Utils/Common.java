package com.nextgenit.doctor.Utils;


import com.nextgenit.doctor.Network.IRetrofitApi;
import com.nextgenit.doctor.Network.RetrofitClient;

public abstract class Common {


    public static final String BASE_URL_XACT = "http://demo.xactidea.com/camelia/api/";

    public static IRetrofitApi getApiXact() {
        return RetrofitClient.getClient(BASE_URL_XACT).create(IRetrofitApi.class);
    }
}
