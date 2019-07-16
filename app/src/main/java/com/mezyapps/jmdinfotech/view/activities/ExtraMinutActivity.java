package com.mezyapps.jmdinfotech.view.activities;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.mezyapps.jmdinfotech.model.ExtraAndLessMinutResponse;
import com.mezyapps.jmdinfotech.R;
import com.mezyapps.jmdinfotech.api_common.ApiClient;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ExtraMinutActivity extends AppCompatActivity {
 private TextView tv_date_picker_extra,tv_time_picker_from_extra,tv_time_picker_to_extra,tv_total_minutes_view;
 private String format;
 private Button extraminut_submit_btn;
 private EditText extaminut_remarks_ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_extra_minut);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
           // getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setTitle("Add Minuts");
        }

        tv_date_picker_extra=findViewById(R.id.tv_date_picker_extra);
        tv_time_picker_from_extra=findViewById(R.id.tv_time_picker_from_extra);
        tv_time_picker_to_extra=findViewById(R.id.tv_time_picker_to_extra);
        extraminut_submit_btn=findViewById(R.id.extraminut_submit_btn);
        extaminut_remarks_ed=findViewById(R.id.extaminut_remarks_ed);
        tv_total_minutes_view=findViewById(R.id.tv_total_minutes_view);




        //date picker start

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String todate= dateFormat.format(currentdate());
        String  currentdate = todate.toString(); //here you get current dat
        tv_date_picker_extra.setText(currentdate);


        tv_date_picker_extra.setOnClickListener(new View.OnClickListener() {
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

                        tv_date_picker_extra.setText(strBuf.toString());
                    }
                };
                // Get current year, month and day.
                Calendar now = Calendar.getInstance();
                int year = now.get(Calendar.YEAR);
                int month = now.get(Calendar.MONTH);
                int day = now.get(Calendar.DAY_OF_MONTH);
                // DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, onDateSetListener, year, month, day);
                DatePickerDialog datePickerDialog = new DatePickerDialog(ExtraMinutActivity.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);
                // Set dialog icon and title.
                //  datePickerDialog.setIcon(R.drawable.appiconn);
                datePickerDialog.setTitle("Please select date.");

                // Popup the dialog.
                datePickerDialog.show();
            }
        });


//date picker end

        //time picker start

        Calendar now = Calendar.getInstance();
        int hourOfDay = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        if (hourOfDay == 0) {
            hourOfDay += 12;
            format = "am";
        }
        else if (hourOfDay == 12) {
            format = "pm";
        }
        else if (hourOfDay > 12) {
            hourOfDay -= 12;
            format = "pm";
        }
        else { format = "am"; }
        String hourString=String.valueOf(hourOfDay);
        String minutString=String.valueOf(minute);
        if (hourString.length() == 1) {
            hourString = "0" + hourString;
        }

        if (minutString.length() == 1) {
            minutString = "0" + minutString;
        }
        tv_time_picker_from_extra.setText(hourString + ":" + minutString + " "+format);
        tv_time_picker_to_extra.setText(hourString + ":" + minutString + " "+format);
        calculateMinute();
        tv_time_picker_from_extra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new OnDateSetListener instance. This listener will be invoked when user click ok button in DatePickerDialog.
                TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay == 0) {
                            hourOfDay += 12;
                            format = "am";
                        } else if (hourOfDay == 12) {
                            format = "pm";
                        } else if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            format = "pm";
                        } else { format = "am"; }
                        String hourString=String.valueOf(hourOfDay);
                        String minutString=String.valueOf(minute);
                        if (hourString.length() == 1) {
                            hourString = "0" + hourString;
                        }

                        if (minutString.length() == 1) {
                            minutString = "0" + minutString;
                        }
                        tv_time_picker_from_extra.setText(hourString + ":" + minutString + " "+format);
                        calculateMinute();
                    }
                };
                // Get current year, month and day.
                Calendar now = Calendar.getInstance();
                int hour = now.get(Calendar.HOUR_OF_DAY);
                int minut = now.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(ExtraMinutActivity.this, android.R.style.Theme_Holo_Dialog, onTimeSetListener,hour,minut,false);
                timePickerDialog.setTitle("please select time.");

                // Popup the dialog.
                timePickerDialog.show();
            }
        });






        tv_time_picker_to_extra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new OnDateSetListener instance. This listener will be invoked when user click ok button in DatePickerDialog.
                TimePickerDialog.OnTimeSetListener onTimeSetListener=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay == 0) {
                            hourOfDay += 12;
                            format = "am";
                        }
                        else if (hourOfDay == 12) {
                            format = "pm";
                        }
                        else if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            format = "pm";
                        } else { format = "am"; }
                        String hourString=String.valueOf(hourOfDay);
                        String minutString=String.valueOf(minute);
                        if (hourString.length() == 1) {
                            hourString = "0" + hourString;
                        }

                        if (minutString.length() == 1) {
                            minutString = "0" + minutString;
                        }
                        tv_time_picker_to_extra.setText(hourString + ":" + minutString + " "+format);
                        calculateMinute();
                    }
                };
                // Get current year, month and day.
                Calendar now = Calendar.getInstance();
                int hour = now.get(Calendar.HOUR_OF_DAY);
                int minut = now.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(ExtraMinutActivity.this, android.R.style.Theme_Holo_Dialog, onTimeSetListener,hour,minut,false);
                timePickerDialog.setTitle("please select time.");

                // Popup the dialog.
                timePickerDialog.show();
            }
        });

//        time picker end


        extraminut_submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ExtraMinutActivity.this);
                alertDialogBuilder.setMessage("Are you sure want to Submit ?");
                alertDialogBuilder.setPositiveButton("yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0, int arg1) {
                                final SweetAlertDialog pDialog = new SweetAlertDialog(ExtraMinutActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                                pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
                                pDialog.setTitleText("Loading");
                                pDialog.setCancelable(false);
                                pDialog.show();


                                ApiClient.getClient().extraMinuts(MainActivity.userId,tv_date_picker_extra.getText().toString().trim(),tv_time_picker_from_extra.getText().toString().trim(),tv_time_picker_to_extra.getText().toString().trim(),extaminut_remarks_ed.getText().toString().trim(),
                                        new Callback<ExtraAndLessMinutResponse>() {
                                            @Override
                                            public void success(ExtraAndLessMinutResponse extraAndLessMinutResponse, Response response) {
                                                if (extraAndLessMinutResponse.getSuccess().equalsIgnoreCase("true")) {
                                                    Toast.makeText(getApplicationContext(), extraAndLessMinutResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                                    pDialog.dismiss();
                                                    tv_time_picker_to_extra.setText("");
                                                    tv_time_picker_from_extra.setText("");
                                                    tv_date_picker_extra.setText("");
                                                    extaminut_remarks_ed.setText("");
                                                } else {
                                                    pDialog.dismiss();
                                                    Toast.makeText(getApplicationContext(), extraAndLessMinutResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                            @Override
                                            public void failure(RetrofitError error) {
                                                pDialog.dismiss();
                                                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        });

                            }
                        });

                alertDialogBuilder.setNegativeButton("No",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();


            }
        });

    }



    private Date currentdate() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        return cal.getTime();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }
private  void calculateMinute(){
    long min = 0;
    long difference ;
    try {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa");
        Date date1 = simpleDateFormat.parse(tv_time_picker_from_extra.getText().toString());
        Date date2 = simpleDateFormat.parse(tv_time_picker_to_extra.getText().toString());

        difference = (date2.getTime() - date1.getTime()) / 1000;
        long hours = difference % (24 * 3600) / 3600; // Calculating Hours
        long minute = difference % 3600 / 60; // Calculating minutes if there is any minutes difference
        min = minute + (hours * 60); // This will be our final minutes. Multiplying by 60 as 1 hour contains 60 mins
        tv_total_minutes_view.setText(String.valueOf(min));
    } catch (Throwable e) {
        e.printStackTrace();
    }
}

}
