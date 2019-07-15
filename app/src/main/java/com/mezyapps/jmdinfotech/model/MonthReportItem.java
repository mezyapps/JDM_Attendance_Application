package com.mezyapps.jmdinfotech.model;

public class MonthReportItem {
    private String logindate;
    private String in_time;
    private String out_time;
    private String add_minut;

    public String getLogindate() {
        return logindate;
    }

    public void setLogindate(String logindate) {
        this.logindate = logindate;
    }

    public String getIn_time() {
        return in_time;
    }

    public void setIn_time(String in_time) {
        this.in_time = in_time;
    }

    public String getOut_time() {
        return out_time;
    }

    public void setOut_time(String out_time) {
        this.out_time = out_time;
    }

    public String getAdd_minut() {
        return add_minut;
    }

    public void setAdd_minut(String add_minut) {
        this.add_minut = add_minut;
    }

    public String getLess_minut() {
        return less_minut;
    }

    public void setLess_minut(String less_minut) {
        this.less_minut = less_minut;
    }

    public String getNet_minut() {
        return net_minut;
    }

    public void setNet_minut(String net_minut) {
        this.net_minut = net_minut;
    }

    public String getWork_time() {
        return work_time;
    }

    public void setWork_time(String work_time) {
        this.work_time = work_time;
    }

    private String less_minut;
    private String net_minut;
    private String work_time;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;
}
