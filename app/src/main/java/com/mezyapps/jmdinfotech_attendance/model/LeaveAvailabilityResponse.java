package com.mezyapps.jmdinfotech_attendance.model;

public class LeaveAvailabilityResponse {
    private String success;
    private String message;

    public String getLeave_status() {
        return leave_status;
    }

    public void setLeave_status(String leave_status) {
        this.leave_status = leave_status;
    }

    private String leave_status;
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

}
