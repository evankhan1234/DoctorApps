package com.nextgenit.doctor.Utils;


import com.nextgenit.doctor.Network.IRetrofitApi;
import com.nextgenit.doctor.Network.RetrofitClient;

public abstract class Common {


    public static final String BASE_URL_XACT = "http://103.245.108.118:8000/mapps/public/api/";

    public static IRetrofitApi getApiXact() {
        return RetrofitClient.getClient(BASE_URL_XACT).create(IRetrofitApi.class);
    }
}
