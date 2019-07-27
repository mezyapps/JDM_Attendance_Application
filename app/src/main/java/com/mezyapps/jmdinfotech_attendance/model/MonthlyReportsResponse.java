package com.mezyapps.jmdinfotech_attendance.model;

import java.util.List;

public class MonthlyReportsResponse {
    private String success;
    private String message;

    public String getDay_min() {
        return day_min;
    }

    public void setDay_min(String day_min) {
        this.day_min = day_min;
    }

    private String day_min;

    public String getTotalnetminut() {
        return totalnetminut;
    }

    public void setTotalnetminut(String totalnetminut) {
        this.totalnetminut = totalnetminut;
    }

    private String totalnetminut;
    public List<MonthReportItem> getMonthReportItems() {
        return monthReportItems;
    }

    public void setMonthReportItems(List<MonthReportItem> monthReportItems) {
        this.monthReportItems = monthReportItems;
    }

    private List<MonthReportItem> monthReportItems = null;



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
