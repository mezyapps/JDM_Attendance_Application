package com.mezyapps.jmdinfotech.view.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.mezyapps.jmdinfotech.Common;
import com.mezyapps.jmdinfotech.Config;
import com.mezyapps.jmdinfotech.model.EmpDetailItem;
import com.mezyapps.jmdinfotech.model.InOutResponse;
import com.mezyapps.jmdinfotech.R;
import com.mezyapps.jmdinfotech.api_common.ApiClient;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  , GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{
    private static final String TAG = "Attendance";
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationRequest mLocationRequest;
    private com.google.android.gms.location.LocationListener listener;
    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    private LocationManager locationManager;
    TextView leave_status;
    private String logintime_response="",logouttime_response="",llocation,TimeAnndDate,tag;
    TextView emp_id_ed,address_ed,time_slot,empname,latemark_v,eaegecy_tv,paid_leav_tv,casual_leavve,sick_leave,balnce_minut,leave_type,tv_time_picker,tv_date_picker;
    TextView tv_login_time_view,tv_logout_time_view;
    public static String userId;
    public static final int RequestPermissionCode = 1;
    String format;
    LinearLayout loginLogout_time_view_layout;
    ImageView profile_image;
    private Toolbar  toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        find_All_IDs();
        events();

    }


    private void find_All_IDs() {
        toolbar = findViewById(R.id.toolbar);
        drawer =  findViewById(R.id.drawer_layout);
        navigationView =findViewById(R.id.nav_view);
        profile_image=findViewById(R.id.profile_image);
        tv_time_picker=findViewById(R.id.tv_time_picker);
        tv_date_picker=findViewById(R.id.tv_date_picker);
        loginLogout_time_view_layout=findViewById(R.id.loginLogout_time_view_layout);
        tv_login_time_view=findViewById(R.id.tv_login_time_view);
        tv_logout_time_view=findViewById(R.id.tv_logout_time_view);

    }


    private void events() {


        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        navigationView.setNavigationItemSelectedListener(this);
        getUserId();
        empname = drawer.findViewById(R.id.name_emp);

//        empname.setText("ESRAFIL ANSARI");


        ///create object area END
//location permission and method call start
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
            locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
            checkLocation();
        } else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, 0);
                mGoogleApiClient = new GoogleApiClient.Builder(this)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();
                locationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
                checkLocation();
            }
        }

//location permission and method call end
        getAllEmpDetails();

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
        tv_time_picker.setText(hourString + ":" + minutString + " "+format);

        tv_time_picker.setOnClickListener(new View.OnClickListener() {
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
                        tv_time_picker.setText(hourString + ":" + minutString + " "+format);
                    }
                };
                // Get current year, month and day.
                Calendar now = Calendar.getInstance();
                int hour = now.get(Calendar.HOUR_OF_DAY);
                int minut = now.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Dialog, onTimeSetListener,hour,minut,false);
                timePickerDialog.setTitle("please select time.");

                // Popup the dialog.
                timePickerDialog.show();
            }
        });

//        time picker end
//date picker start

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String todate= dateFormat.format(currentdate());
        String  currentdate = todate.toString(); //here you get current dat
        tv_date_picker.setText(currentdate);


        tv_date_picker.setOnClickListener(new View.OnClickListener() {
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

                        tv_date_picker.setText(strBuf.toString());
                        getInOUTTagData();
                    }
                };
                // Get current year, month and day.
                Calendar now = Calendar.getInstance();
                int year = now.get(Calendar.YEAR);
                int month = now.get(Calendar.MONTH);
                int day = now.get(Calendar.DAY_OF_MONTH);
                // DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, onDateSetListener, year, month, day);
                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);
                // Set dialog icon and title.
                //  datePickerDialog.setIcon(R.drawable.appiconn);
                datePickerDialog.setTitle("Please select date.");

                // Popup the dialog.
                datePickerDialog.show();
            }
        });


//date picker end


        //leave_staus();
        getInOUTTagData();

    }


    private void getInOUTTagData() {
        ApiClient.getClient().getInOUTTagData(userId,tv_date_picker.getText().toString().trim(),
                new Callback<InOutResponse>() {
                    @Override
                    public void success(InOutResponse inOutResponse, Response response) {
                        if (inOutResponse.getSuccess().equalsIgnoreCase("true")) {
                          tag=inOutResponse.getTag();
                            attendanceButton();
                        }else if (inOutResponse.getSuccess().equalsIgnoreCase("false")){
                            tag=inOutResponse.getTag();
                            logintime_response=inOutResponse.getLogin_time();
                            logouttime_response=inOutResponse.getLogout_time();
                            attendanceButton();
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @SuppressLint("ResourceAsColor")
    private void attendanceButton() {
        LinearLayout layout = findViewById(R.id.llayout);
        layout.removeAllViews();
        if (tag.equals("login")) {
            loginLogout_time_view_layout.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
            layout.setOrientation(LinearLayout.VERTICAL);
            final Button btn = new Button(this);
            btn.setText("LOGIN");
            btn.setTextColor(Color.WHITE);
            btn.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            layout.addView(btn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setMessage("Are you sure want to Login ?");
                    alertDialogBuilder.setPositiveButton("yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    final SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                                    pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
                                    pDialog.setTitleText("Loading");
                                    pDialog.setCancelable(false);
                                    pDialog.show();
                                    ApiClient.getClient().in(userId,tv_date_picker.getText().toString().trim(),tv_time_picker.getText().toString().trim(),llocation,
                                            new Callback<InOutResponse>() {
                                                @Override
                                                public void success(InOutResponse inOutResponse, Response response) {
                                                      Toast.makeText(getApplicationContext(), inOutResponse.getResponse(), Toast.LENGTH_SHORT).show();
                                                      pDialog.dismiss();
                                                    getInOUTTagData();
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

        } else if (tag.equals("logout")) {
            loginLogout_time_view_layout.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
            layout.setOrientation(LinearLayout.VERTICAL);
            final Button btn2 = new Button(this);
            btn2.setBackgroundColor(R.color.colorPrimaryDark);
            btn2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            btn2.setTextColor(Color.WHITE);
            btn2.setText("LOGOUT");
            layout.addView(btn2);

            btn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setMessage("Are you sure want to Logout ?");
                    alertDialogBuilder.setPositiveButton("yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0, int arg1) {
                                    final SweetAlertDialog pDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                                    pDialog.getProgressHelper().setBarColor(getResources().getColor(R.color.colorPrimary));
                                    pDialog.setTitleText("Loading");
                                    pDialog.setCancelable(false);
                                    pDialog.show();
                                    ApiClient.getClient().out(userId,tv_date_picker.getText().toString().trim(),tv_time_picker.getText().toString().trim(),llocation,
                                            new Callback<InOutResponse>() {
                                                @Override
                                                public void success(InOutResponse inOutResponse, Response response) {
                                                    Toast.makeText(getApplicationContext(), inOutResponse.getResponse(), Toast.LENGTH_SHORT).show();
                                                    pDialog.dismiss();
                                                    getInOUTTagData();
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
        }else if (tag.equals("used")){
//            layout.setOrientation(LinearLayout.VERTICAL);
//            final Button btn2 = new Button(this);
//            btn2.setBackgroundColor(R.color.colorPrimaryDark);
//            btn2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
//            btn2.setTextColor(Color.WHITE);
//            btn2.setText("Done");
//            layout.addView(btn2);
            layout.setVisibility(View.GONE);

            loginLogout_time_view_layout.setVisibility(View.VISIBLE);
            tv_logout_time_view.setText(logouttime_response);
            tv_login_time_view.setText(logintime_response);
        } else {
            loginLogout_time_view_layout.setVisibility(View.GONE);
            layout.setVisibility(View.VISIBLE);
            layout.setOrientation(LinearLayout.VERTICAL);
            final Button btn2 = new Button(this);
            btn2.setBackgroundColor(R.color.colorPrimaryDark);
            btn2.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
            btn2.setTextColor(Color.WHITE);
            btn2.setText("No Allowed ");
            layout.addView(btn2);

        }



    }


    // end login logout tag end
    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_extra_minut) {
            startActivity(new Intent(MainActivity.this, ExtraMinutActivity.class));
        } else if (id == R.id.nav_less_miut) {
            startActivity(new Intent(MainActivity.this, LessMinutActivity.class));
        } else if (id == R.id.nav_month_wise_rport) {
            startActivity(new Intent(MainActivity.this, MonthWiseReport.class));
        } else if (id == R.id.nav_leaves) {
            startActivity(new Intent(MainActivity.this, LeavsActivity.class));
        } else if (id == R.id.nav_share) {
            shareApp();
        } else if (id == R.id.nav_send) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://jmd-infotech.com")));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;


    }

    private void logout() {
        final SweetAlertDialog alertDialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
        alertDialog.setTitleText("Are you sure you want to logout?");
        alertDialog.setCancelText("Cancel");
        alertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                alertDialog.dismissWithAnimation();
            }
        });
        alertDialog.show();
        Button btn = (Button) alertDialog.findViewById(R.id.confirm_button);
        btn.setBackground(getResources().getDrawable(R.drawable.custom_dialog_button));
        btn.setText("Logout");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.saveUserData(MainActivity.this, "mobile", "");
                Common.saveUserData(MainActivity.this, "userId", "");
                Config.moveTo(MainActivity.this, LoginActivity.class);
                finish();

            }
        });
    }


    //get detail end
    //getelocatio start

    //get Location
    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }

        startLocationUpdates();

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(mLocation == null){
            startLocationUpdates();

        }
        if (mLocation != null) {

            // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
            //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
        } else {

            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected void startLocationUpdates() {

        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(UPDATE_INTERVAL)
                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }

    @Override
    public void onLocationChanged(Location location) {

        String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        //  tttt.setText(String.valueOf(location.getLatitude())+String.valueOf(location.getLongitude() ));
        llocation = location.getLatitude() + "," + location.getLongitude();

        // You can now create a LatLng Object for use with maps
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        try {

            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
//            tttt.setText(tttt.getText() + "\n" + addresses.get(0).getAddressLine(0) + ", " +
//                    addresses.get(0).getAddressLine(1) + ", " + addresses.get(0).getAddressLine(2));
            llocation = location.getLatitude() + "," + location.getLongitude();
        } catch (Exception e) {

        }
    }

    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final android.support.v7.app.AlertDialog.Builder dialog = new android.support.v7.app.AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to ")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        finish();
                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    //getlocation end

//    private void leave_staus() {
//        Call<EmpDetailItem> call = apiInterface.leave_staus(emp_id);
//        call.enqueue(new Callback<EmpDetailItem>() {
//            @Override
//            public void onResponse(Call<EmpDetailItem> call, Response<EmpDetailItem> response) {
//
//                String leave_date = response.body().getLeave_date();
//                String leave_sttus = response.body().getLeave_status();
//                String leave_typ = response.body().getLeave_type();
//                leave_type.setText("Leave Type: "+leave_typ);
//                leave_status.setText("Date: "+leave_date + " " +" Status: "+ leave_sttus);
//
//
//            }
//
//            @Override
//            public void onFailure(Call<EmpDetailItem> call, Throwable t) {
//
//            }
//        });
//    }

    private void getUserId() {
        if (Common.getSavedUserData(MainActivity.this, "userId").equalsIgnoreCase("")) {
            userId = "";
        } else {
            userId = Common.getSavedUserData(MainActivity.this, "userId");
            Log.d("userId", userId);
        }

    }
    private Date currentdate() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 0);
        return cal.getTime();
    }




//    permission requestPermission

    private void requestPermission() {

        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {
                        ACCESS_FINE_LOCATION,
                        ACCESS_COARSE_LOCATION,
                }, RequestPermissionCode);

    }






    public void getAllEmpDetails() {
        ApiClient.getClient().empDetails(MainActivity.userId,
                new Callback<EmpDetailItem>() {
                    @Override
                    public void success(EmpDetailItem empDetailItem, Response response) {

                        if (empDetailItem.getSuccess().equalsIgnoreCase("false")){
                            Toast.makeText(MainActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }else {

                            empname.setText(empDetailItem.getFirst_name()+" "+empDetailItem.getLast_name());
                            Picasso.with(MainActivity.this).load(empDetailItem.getImage())
                                    .placeholder(R.drawable.defaultimage)
                                    .error(R.drawable.defaultimage)
                                    .into(profile_image);
                        }

                    }
                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(), "connection error", Toast.LENGTH_SHORT).show();
                    }
                });

    }
    public void shareApp() {
        // share app with your friends
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/*");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Try this " + getResources().getString(R.string.app_name) + " App: https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
        startActivity(Intent.createChooser(shareIntent, "Share Using"));
    }
}
