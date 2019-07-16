package com.mezyapps.jmdinfotech.view.activities;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;

import com.mezyapps.jmdinfotech.api_common.ApiInterface1;
import com.mezyapps.jmdinfotech.model.SuccessModule;
import com.mezyapps.jmdinfotech.utils.SharedUtils;
import com.mezyapps.jmdinfotech.Config;
import com.mezyapps.jmdinfotech.model.SignUpResponse;
import com.mezyapps.jmdinfotech.R;
import com.mezyapps.jmdinfotech.api_common.ApiClient;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    private String string_username;
    private TextInputEditText username_tie;
    private Button btn_Login;
    private static ApiInterface1 apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        find_All_IDs();
        events();

    }


    private void find_All_IDs() {
        apiInterface = ApiClient.getClient1().create(ApiInterface1.class);
        username_tie = findViewById(R.id.ed_username_login);
        btn_Login = findViewById(R.id.btn_login);

    }

    private void events() {

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (validation()) {
                    performLogin();
                }

            }
        });

    }

    private boolean validation() {
        string_username = username_tie.getText().toString().trim();
        if (string_username.equals("")) {
            Toast.makeText(LoginActivity.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        } else if (string_username.length() != 10) {
            Toast.makeText(LoginActivity.this, "Invalid Mobile Number", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void performLogin() {
        final SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorAccent));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();

        Call<SuccessModule> call = apiInterface.login(string_username);

        call.enqueue(new Callback<SuccessModule>() {
            @Override
            public void onResponse(Call<SuccessModule> call, Response<SuccessModule> response) {

                String str_response = new Gson().toJson(response.body());
                Log.d("Response >>", str_response);

                try {
                    if (response.isSuccessful()) {
                        SuccessModule successModule = response.body();

                        String message = null, code = null;
                        if (successModule != null) {
                            message = successModule.getSuccess();
                            //code = successModule.getCode();
                            if (message.equalsIgnoreCase("true")) {
                                pDialog.dismiss();
                                SharedUtils.saveUserData(LoginActivity.this, "is_login", "true");
                                SharedUtils.saveUserData(LoginActivity.this, "userId", successModule.getEmp_id() + "");
                                Config.moveTo(LoginActivity.this, MainActivity.class);
                                finishAffinity();
                            } else {
                                pDialog.dismiss();
                                Toast.makeText(getApplicationContext(), successModule.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(LoginActivity.this, "Response Null", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<SuccessModule> call, Throwable t) {

            }
        });

    }


}



