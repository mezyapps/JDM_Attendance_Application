package com.mezyapps.jmdinfotech.activities;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.mezyapps.jmdinfotech.model.MonthReportItem;
import com.mezyapps.jmdinfotech.model.MonthlyReportsResponse;
import com.mezyapps.jmdinfotech.R;
import com.mezyapps.jmdinfotech.Retrofit.Api;
import com.mezyapps.jmdinfotech.adapters.MonthlyReportsAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MonthWiseReport extends AppCompatActivity {
RecyclerView monthly_report_recyclerview;
    public static MonthlyReportsResponse monthlyReportsResponseslist;
    public static List<MonthReportItem> monthWiseReports = new ArrayList<>();
    TextView total_net_minuts_report,total_balance_minuts_report,total_day_minuts_report;
    private TextView monthly_report_to_date,monthly_report_from_date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_wise_report);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            // getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setTitle("Monthly Reports");
        }
        monthly_report_to_date=findViewById(R.id.monthly_report_to_date);
        monthly_report_from_date=findViewById(R.id.monthly_report_from_date);
        monthly_report_recyclerview=findViewById(R.id.monthly_report_recyclerview);
        total_net_minuts_report=findViewById(R.id.total_net_minuts_report);
        total_balance_minuts_report=findViewById(R.id.total_balance_minuts_report);
        total_day_minuts_report=findViewById(R.id.total_day_minuts_report);
        monthly_report_recyclerview.setNestedScrollingEnabled(false);





        //date picker start
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
      //  Toast.makeText(this, ""+String.valueOf(dateFormat.format(date)), Toast.LENGTH_SHORT).show();

        Calendar gc = new GregorianCalendar();
        gc.set(Calendar.MONTH, Integer.valueOf(dateFormat.format(date))-1);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        Date monthStart = gc.getTime();
        gc.add(Calendar.MONTH, 1);
        gc.add(Calendar.DAY_OF_MONTH, -1);
        Date monthEnd = gc.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");


        monthly_report_from_date.setText(simpleDateFormat.format(monthStart));
        monthly_report_to_date.setText(simpleDateFormat.format(monthEnd));


        monthly_report_from_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        StringBuffer strBuf = new StringBuffer();
                        String  monthString=String.valueOf(month+1);
                        String dateString=String.valueOf(dayOfMonth);
                        if (monthString.length() == 1) {
                            monthString = "0" + monthString;
                        }
                        if (dateString.length() == 1) {
                            dateString = "0" + dateString;
                        }
                        strBuf.append(dateString);
                        strBuf.append("-");
                        strBuf.append(monthString);
                        strBuf.append("-");
                        strBuf.append(year);

                        monthly_report_from_date.setText(strBuf.toString());
                    }
                };
                // Get current year, month and day.
                Calendar now = Calendar.getInstance();
                int year = now.get(Calendar.YEAR);
                int month = now.get(Calendar.MONTH);
                int day = now.get(Calendar.DAY_OF_MONTH);
                // DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, onDateSetListener, year, month, day);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MonthWiseReport.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);
                // Set dialog icon and title.
                //  datePickerDialog.setIcon(R.drawable.appiconn);
                datePickerDialog.setTitle("Please select date.");

                // Popup the dialog.
                datePickerDialog.show();
            }
        });



        monthly_report_to_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        StringBuffer strBuf = new StringBuffer();

                        String  monthString=String.valueOf(month+1);
                        String dateString=String.valueOf(dayOfMonth);
                        if (monthString.length() == 1) {
                            monthString = "0" + monthString;
                        }
                        if (dateString.length() == 1) {
                            dateString = "0" + dateString;
                        }
                        strBuf.append(dateString);
                        strBuf.append("-");
                        strBuf.append(monthString);
                        strBuf.append("-");
                        strBuf.append(year);

                        monthly_report_to_date.setText(strBuf.toString());
                    }
                };
                // Get current year, month and day.
                Calendar now = Calendar.getInstance();
                int year = now.get(Calendar.YEAR);
                int month = now.get(Calendar.MONTH);
                int day = now.get(Calendar.DAY_OF_MONTH);
                // DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, onDateSetListener, year, month, day);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MonthWiseReport.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);
                // Set dialog icon and title.
                //  datePickerDialog.setIcon(R.drawable.appiconn);
                datePickerDialog.setTitle("Please select date.");

                // Popup the dialog.
                datePickerDialog.show();
            }
        });
//date picker end

    }


    public void getReportMethod(View view) {
        getAllMonthWiseReport();
    }

    public void getAllMonthWiseReport() {
        // getting news list data
        final SweetAlertDialog pDialog = new SweetAlertDialog(MonthWiseReport.this, SweetAlertDialog.PROGRESS_TYPE);
        pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
        pDialog.setTitleText("Loading");
        pDialog.setCancelable(false);
        pDialog.show();
        Api.getClient().monthlyReports(MainActivity.userId,monthly_report_from_date.getText().toString().trim(),monthly_report_to_date.getText().toString().trim(),
                new Callback<MonthlyReportsResponse>() {
                    @Override
                    public void success(MonthlyReportsResponse monthlyReportsResponses, Response response) {
                        monthlyReportsResponseslist = monthlyReportsResponses;
                        monthWiseReports=monthlyReportsResponses.getMonthReportItems();
                        pDialog.hide();
                        if (monthlyReportsResponses.getSuccess().equalsIgnoreCase("false")){
                            Toast.makeText(MonthWiseReport.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }else {
                            setDataInRecyclerViewAsk();
                            total_net_minuts_report.setText(monthlyReportsResponses.getTotalnetminut());
                            total_day_minuts_report.setText(monthlyReportsResponses.getDay_min());
                            total_balance_minuts_report.setText(String.valueOf(Integer.valueOf(monthlyReportsResponses.getTotalnetminut())-Integer.valueOf(monthlyReportsResponses.getDay_min())));
                        }

                    }
                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(), "try again", Toast.LENGTH_SHORT).show();
                        pDialog.hide();

                    }
                });

    }

    private void setDataInRecyclerViewAsk() {
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        monthly_report_recyclerview.setLayoutManager(linearLayoutManager);

        MonthlyReportsAdapter memberwiseAskAdapter = new MonthlyReportsAdapter(getApplicationContext(), monthWiseReports);
        monthly_report_recyclerview.setAdapter(memberwiseAskAdapter); // set the Adapter to RecyclerView
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }


}
