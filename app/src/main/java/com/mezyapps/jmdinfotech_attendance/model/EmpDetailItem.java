package com.mezyapps.jmdinfotech_attendance.model;

import java.util.HashMap;
import java.util.Map;

public class EmpDetailItem {

    private String first_name;
    private String last_name;
    private String time_slot;
    private String image;
    private String no_late_mk;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getTime_slot() {
        return time_slot;
    }

    public void setTime_slot(String time_slot) {
        this.time_slot = time_slot;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNo_late_mk() {
        return no_late_mk;
    }

    public void setNo_late_mk(String no_late_mk) {
        this.no_late_mk = no_late_mk;
    }

    public String getEmrgency() {
        return emrgency;
    }

    public void setEmrgency(String emrgency) {
        this.emrgency = emrgency;
    }

    public String getPaid_leave() {
        return paid_leave;
    }

    public void setPaid_leave(String paid_leave) {
        this.paid_leave = paid_leave;
    }

    public String getCasual_leave() {
        return casual_leave;
    }

    public void setCasual_leave(String casual_leave) {
        this.casual_leave = casual_leave;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getUse_late_mk() {
        return use_late_mk;
    }

    public void setUse_late_mk(String use_late_mk) {
        this.use_late_mk = use_late_mk;
    }

    public String getUse_emrgency() {
        return use_emrgency;
    }

    public void setUse_emrgency(String use_emrgency) {
        this.use_emrgency = use_emrgency;
    }

    public String getUse_paid_leave() {
        return use_paid_leave;
    }

    public void setUse_paid_leave(String use_paid_leave) {
        this.use_paid_leave = use_paid_leave;
    }

    public String getUse_casual_leave() {
        return use_casual_leave;
    }

    public void setUse_casual_leave(String use_casual_leave) {
        this.use_casual_leave = use_casual_leave;
    }

    public String getUse_sick_leave() {
        return use_sick_leave;
    }

    public void setUse_sick_leave(String use_sick_leave) {
        this.use_sick_leave = use_sick_leave;
    }

    public String getSick_leave() {
        return sick_leave;
    }

    public void setSick_leave(String sick_leave) {
        this.sick_leave = sick_leave;
    }

    public String getLeave_date() {
        return leave_date;
    }

    public void setLeave_date(String leave_date) {
        this.leave_date = leave_date;
    }

    public String getLeave_status() {
        return leave_status;
    }

    public void setLeave_status(String leave_status) {
        this.leave_status = leave_status;
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }

    public String getTotal_time() {
        return total_time;
    }

    public void setTotal_time(String total_time) {
        this.total_time = total_time;
    }

    public String getLeave_type() {
        return leave_type;
    }

    public void setLeave_type(String leave_type) {
        this.leave_type = leave_type;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    private String emrgency;
    private String paid_leave;
    private String casual_leave;
    private String tag;
    private String use_late_mk;
    private String use_emrgency;
    private String use_paid_leave;
    private String use_casual_leave;
    private String use_sick_leave;
    private String sick_leave;
    private String leave_date;
    private String leave_status;
    private String work_time;
    private String total_time;
    private String leave_type;
    private String success;
    private String message;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();
}
