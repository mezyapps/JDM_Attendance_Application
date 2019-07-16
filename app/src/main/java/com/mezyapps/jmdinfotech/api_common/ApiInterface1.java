package com.mezyapps.jmdinfotech.api_common;

import com.mezyapps.jmdinfotech.model.SuccessModule;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface1 {

    @POST(EndApi.LOGIN)
    @FormUrlEncoded
    Call<SuccessModule> login(@Field("mobile") String mobile);

}
