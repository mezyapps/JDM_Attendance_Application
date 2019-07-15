package com.mezyapps.jmdinfotech.activities;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mezyapps.jmdinfotech.Common;
import com.mezyapps.jmdinfotech.Config;
import com.mezyapps.jmdinfotech.model.SignUpResponse;
import com.mezyapps.jmdinfotech.R;
import com.mezyapps.jmdinfotech.Retrofit.Api;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends AppCompatActivity {

    String string_username;
    TextInputEditText ed_paasword_login,username_tie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

       username_tie=findViewById(R.id.ed_username_login);

        if (Common.getSavedUserData(LoginActivity.this, "mobile").equalsIgnoreCase("")) {

        } else {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finishAffinity();
        }
        Button btn_Login=findViewById(R.id.btn_login);
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                string_username = username_tie.getText().toString().trim();
                if (string_username.equals("") ){
                    Toast.makeText(LoginActivity.this, "Required Field", Toast.LENGTH_SHORT).show();
                }
                else {
                    performLogin();
                }
            }
        });

    }

    private void performLogin() {

            final SweetAlertDialog pDialog = new SweetAlertDialog(LoginActivity.this, SweetAlertDialog.PROGRESS_TYPE);
            pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
            pDialog.setTitleText("Loading");
            pDialog.setCancelable(false);
            pDialog.show();
           Api.getClient().login(string_username,
                new Callback<SignUpResponse>() {
                    @Override
                    public void success(SignUpResponse signUpResponse, Response response) {
                        if (signUpResponse.getSuccess().equalsIgnoreCase("true")) {
                            pDialog.dismiss();
                            Common.saveUserData(LoginActivity.this, "mobile", string_username);
                            Common.saveUserData(LoginActivity.this, "userId", signUpResponse.getEmp_id()+"");
                            Config.moveTo(LoginActivity.this, MainActivity.class);
                            finishAffinity();
                        } else {
                            pDialog.dismiss();
                            Toast.makeText(getApplicationContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        pDialog.dismiss();
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();


                    }
                });





    }

}

