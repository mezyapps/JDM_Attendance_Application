package com.mezyapps.jmdinfotech.api_common;



import com.mezyapps.jmdinfotech.model.EmpDetailItem;
import com.mezyapps.jmdinfotech.model.ExtraAndLessMinutResponse;
import com.mezyapps.jmdinfotech.model.InOutResponse;
import com.mezyapps.jmdinfotech.model.LeaveAvailabilityResponse;
import com.mezyapps.jmdinfotech.model.MonthlyReportsResponse;
import com.mezyapps.jmdinfotech.model.SignUpResponse;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("/attendance/app/monthlyreports.php")
    public void monthlyReports(@Field("emp_id") String user_id,
                               @Field("from_date") String from_date,
                               @Field("to_date") String to_date,
                               Callback <MonthlyReportsResponse> callback);
    @FormUrlEncoded
    @POST("/attendance/app/get_emp_detail.php")
    public void empDetails(@Field("emp_id") String user_id,
                               Callback <EmpDetailItem> callback);

    @FormUrlEncoded
    @POST("/attendance/app/login.php")
    public void login(@Field("mobile") String mobile,
                      Callback<SignUpResponse> callback);

    @FormUrlEncoded
    @POST("/attendance/app/get_tag.php")
    public void getInOUTTagData(@Field("emp_id") String userId,
                                @Field("login_date") String login_date,
                                Callback<InOutResponse> callback);


    @FormUrlEncoded
    @POST("/attendance/app/check_leave_availability.php")
    public void checkLeaveAvailability(@Field("emp_id") String userId,
                                       @Field("from_date") String from_date,
                                       @Field("to_date") String to_date,
                                       @Field("leave_type") String login_date,
                                       Callback<LeaveAvailabilityResponse> callback);


    @FormUrlEncoded
    @POST("/attendance/app/leave_submit.php")
    public void submitLeave(@Field("emp_id") String userId,
                            @Field("from_date") String from_date,
                            @Field("to_date") String to_date,
                            @Field("leave_type") String login_date,
                            @Field("remarks") String remarks,
                            @Field("dr_prescription") String encodedimage,
                            Callback<LeaveAvailabilityResponse> callback);

    @FormUrlEncoded
    @POST("/attendance/app/in.php")
    public void in(@Field("emp_id") String user_id,
                   @Field("login_date") String login_date,
                   @Field("login_time") String login_time,
                   @Field("location") String location,
                   Callback<InOutResponse> callback);

    @FormUrlEncoded
    @POST("/attendance/app/out.php")
    public void out(@Field("emp_id") String user_id,
                    @Field("login_date") String login_date,
                    @Field("login_time") String login_time,
                    @Field("location") String location,
                    Callback<InOutResponse> callback);


    @FormUrlEncoded
    @POST("/attendance/app/extraminuts.php")
    public void extraMinuts(@Field("emp_id") String user_id,
                            @Field("extra_less_work_date") String extra_work_date,
                            @Field("from_time") String from_time,
                            @Field("to_time") String to_time,
                            @Field("remarks") String remarks,
                            Callback<ExtraAndLessMinutResponse> callback);

    @FormUrlEncoded
    @POST("/attendance/app/lessaminuts.php")
    public void lessMinuts(@Field("emp_id") String user_id,
                           @Field("extra_less_work_date") String extra_work_date,
                           @Field("from_time") String from_time,
                           @Field("to_time") String to_time,
                           @Field("remarks") String remarks,
                           Callback<ExtraAndLessMinutResponse> callback);

}
