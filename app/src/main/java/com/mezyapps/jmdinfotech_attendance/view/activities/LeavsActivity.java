package com.mezyapps.jmdinfotech_attendance.view.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mezyapps.jmdinfotech_attendance.model.LeaveAvailabilityResponse;
import com.mezyapps.jmdinfotech_attendance.R;
import com.mezyapps.jmdinfotech_attendance.api_common.ApiClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LeavsActivity extends AppCompatActivity {
Spinner leave_types_spinnner;
TextView tv_date_picker_leaves_from,tv_date_picker_leaves_to;
RelativeLayout imagepicker_relative_layout;
ImageView image_view_doctor_report;
private  String leavTypeString="";
private Button leave_submit_btn;
private EditText leave_types_remarks_ed;
    private static final String IMAGE_DIRECTORY = "/JMDINFOTECH";
    private int GALLERY = 1, CAMERA = 2;
private  String encodedimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leavs);
        leave_types_spinnner=findViewById(R.id.leave_types_spinnner);
        imagepicker_relative_layout=findViewById(R.id.imagepicker_relative_layout);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            // getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setTitle("Leaves");
        }
        tv_date_picker_leaves_from=findViewById(R.id.tv_date_picker_leaves_from);
        tv_date_picker_leaves_to=findViewById(R.id.tv_date_picker_leaves_to);
        image_view_doctor_report=findViewById(R.id.image_view_doctor_report);
        leave_submit_btn=findViewById(R.id.leave_submit_btn);
        leave_types_remarks_ed=findViewById(R.id.leave_types_remarks_ed);
        //date picker start

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String todate= dateFormat.format(currentdate());
        String  currentdate = todate.toString(); //here you get current dat
        tv_date_picker_leaves_from.setText(currentdate);
        tv_date_picker_leaves_to.setText(currentdate);

        tv_date_picker_leaves_from.setOnClickListener(new View.OnClickListener() {
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

                        tv_date_picker_leaves_from.setText(strBuf.toString());
                    }
                };
                // Get current year, month and day.
                Calendar now = Calendar.getInstance();
                int year = now.get(Calendar.YEAR);
                int month = now.get(Calendar.MONTH);
                int day = now.get(Calendar.DAY_OF_MONTH);
                // DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, onDateSetListener, year, month, day);
                DatePickerDialog datePickerDialog = new DatePickerDialog(LeavsActivity.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);
                // Set dialog icon and title.
                //  datePickerDialog.setIcon(R.drawable.appiconn);
                datePickerDialog.setTitle("Please select date.");

                // Popup the dialog.
                datePickerDialog.show();
            }
        });


        tv_date_picker_leaves_to.setOnClickListener(new View.OnClickListener() {
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

                        tv_date_picker_leaves_to.setText(strBuf.toString());
                    }
                };
                // Get current year, month and day.
                Calendar now = Calendar.getInstance();
                int year = now.get(Calendar.YEAR);
                int month = now.get(Calendar.MONTH);
                int day = now.get(Calendar.DAY_OF_MONTH);
                // DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, onDateSetListener, year, month, day);
                DatePickerDialog datePickerDialog = new DatePickerDialog(LeavsActivity.this, android.R.style.Theme_Holo_Dialog, onDateSetListener, year, month, day);
                // Set dialog icon and title.
                //  datePickerDialog.setIcon(R.drawable.appiconn);
                datePickerDialog.setTitle("Please select date.");

                // Popup the dialog.
                datePickerDialog.show();
            }
        });
//date picker end



        final ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(LeavsActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.leaves_types));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        leave_types_spinnner.setAdapter(myAdapter);

        leave_types_spinnner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    encodedimage=null;
                    imagepicker_relative_layout.setVisibility(View.GONE);
                    leave_submit_btn.setVisibility(View.GONE);
                } else if (i == 1) {
                    imagepicker_relative_layout.setVisibility(View.GONE);
                    leavTypeString="P";
                    encodedimage=null;
                    checkLeaveAvailability(leavTypeString);
                } else if (i == 2) {
                    leavTypeString="C";
                    encodedimage=null;
                    imagepicker_relative_layout.setVisibility(View.GONE);
                    checkLeaveAvailability(leavTypeString);
                } else if (i == 3) {
                    leavTypeString="S";
                    encodedimage=null;
                    checkLeaveAvailability(leavTypeString);
                    imagepicker_relative_layout.setVisibility(View.VISIBLE);
                } else if (i == 4) {
                    leavTypeString="E";
                    encodedimage=null;
                    imagepicker_relative_layout.setVisibility(View.GONE);
                    checkLeaveAvailability(leavTypeString);
                } }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void checkLeaveAvailability(String leavTypeString) {
        // getting news list data
        ApiClient.getClient().checkLeaveAvailability(MainActivity.userId,tv_date_picker_leaves_from.getText().toString().trim(),tv_date_picker_leaves_to.getText().toString().trim(),leavTypeString,
                new Callback<LeaveAvailabilityResponse>() {
                    @Override
                    public void success(LeaveAvailabilityResponse leaveAvailabilityResponse, Response response) {
                        if (leaveAvailabilityResponse.getSuccess().equalsIgnoreCase("true")){
                            leave_submit_btn.setVisibility(View.VISIBLE);
                            Toast.makeText(LeavsActivity.this, ""+leaveAvailabilityResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }else if (leaveAvailabilityResponse.getSuccess().equalsIgnoreCase("false")){
                            leave_submit_btn.setVisibility(View.GONE);
                            Toast.makeText(LeavsActivity.this, ""+leaveAvailabilityResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        } else {
                            leave_submit_btn.setVisibility(View.GONE);
                            Toast.makeText(LeavsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getApplicationContext(), "connection error", Toast.LENGTH_SHORT).show();
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

    public void imageChooseMethod(View view) {
        if (ContextCompat.checkSelfPermission(LeavsActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(LeavsActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            AlertDialog.Builder pictureDialog = new AlertDialog.Builder(LeavsActivity.this);
            pictureDialog.setTitle("Select Action");
            String[] pictureDialogItems = {
                    "Select photo from gallery",
                    "Capture photo from camera" };
            pictureDialog.setItems(pictureDialogItems,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    choosePhotoFromGallary();
                                    break;
                                case 1:
                                    takePhotoFromCamera();
                                    break;
                            }
                        }
                    });
            pictureDialog.show();
        } else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                AlertDialog.Builder pictureDialog = new AlertDialog.Builder(LeavsActivity.this);
                pictureDialog.setTitle("Select Action");
                String[] pictureDialogItems = {
                        "Select photo from gallery",
                        "Capture photo from camera" };
                pictureDialog.setItems(pictureDialogItems,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        choosePhotoFromGallary();
                                        break;
                                    case 1:
                                        takePhotoFromCamera();
                                        break;
                                }
                            }
                        });
                pictureDialog.show();
            }
        }

    }

    public void leavesSubmitbtnMethod(View view) {
        String remarks=leave_types_remarks_ed.getText().toString().trim();

        if (remarks.isEmpty() || leavTypeString.isEmpty()){
            Toast.makeText(this, "Remarks Requierds", Toast.LENGTH_SHORT).show();
        }else{
            if (leavTypeString.equals("S")){
                if (encodedimage == null){
                    Toast.makeText(this, "Image Requierds", Toast.LENGTH_SHORT).show();
                }else{
                ApiClient.getClient().submitLeave(MainActivity.userId,tv_date_picker_leaves_from.getText().toString().trim(),tv_date_picker_leaves_to.getText().toString().trim(),leavTypeString,remarks,encodedimage,
                        new Callback<LeaveAvailabilityResponse>() {
                            @Override
                            public void success(LeaveAvailabilityResponse leaveAvailabilityResponse, Response response) {
                                if (leaveAvailabilityResponse.getSuccess().equalsIgnoreCase("true")){
                                    leave_types_remarks_ed.setText("");
                                    leave_types_spinnner.setSelection(0);
                                    leave_submit_btn.setVisibility(View.GONE);
                                    Toast.makeText(LeavsActivity.this, ""+leaveAvailabilityResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }else if (leaveAvailabilityResponse.getSuccess().equalsIgnoreCase("false")){
                                    Toast.makeText(LeavsActivity.this, ""+leaveAvailabilityResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LeavsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void failure(RetrofitError error) {
                                Toast.makeText(getApplicationContext(), "connection error", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
    } else{
                encodedimage="";
                ApiClient.getClient().submitLeave(MainActivity.userId,tv_date_picker_leaves_from.getText().toString().trim(),tv_date_picker_leaves_to.getText().toString().trim(),leavTypeString,remarks,encodedimage,
                        new Callback<LeaveAvailabilityResponse>() {
                            @Override
                            public void success(LeaveAvailabilityResponse leaveAvailabilityResponse, Response response) {
                                if (leaveAvailabilityResponse.getSuccess().equalsIgnoreCase("true")){
                                    leave_types_remarks_ed.setText("");
                                    leave_types_spinnner.setSelection(0);
                                    leave_submit_btn.setVisibility(View.GONE);
                                    Toast.makeText(LeavsActivity.this, ""+leaveAvailabilityResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                }else if (leaveAvailabilityResponse.getSuccess().equalsIgnoreCase("false")){
                                    Toast.makeText(LeavsActivity.this, ""+leaveAvailabilityResponse.getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(LeavsActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                            @Override
                            public void failure(RetrofitError error) {
                                Toast.makeText(getApplicationContext(), "connection error", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        }
        }






    ///new picker start




    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    image_view_doctor_report.setImageBitmap(bitmap);

                    //                    update pick start
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG,60,byteArrayOutputStream);
                    byte[] imgByte = byteArrayOutputStream.toByteArray();
                      encodedimage= Base64.encodeToString(imgByte,Base64.DEFAULT);
                 //  uodateimage(encodedimage);

//                    update pick end



                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(LeavsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }

        } else if (requestCode == CAMERA) {
            Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
            image_view_doctor_report.setImageBitmap(thumbnail);
            saveImage(thumbnail);
            //                    update pick start
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.JPEG,60,byteArrayOutputStream);
            byte[] imgByte = byteArrayOutputStream.toByteArray();
              encodedimage= Base64.encodeToString(imgByte,Base64.DEFAULT);
           // uodateimage(encodedimage);

//                    update pick end


        }
    }

    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(LeavsActivity.this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }



    /// end picker end




}
