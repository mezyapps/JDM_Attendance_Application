package com.mezyapps.jmdinfotech_attendance.view.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mezyapps.jmdinfotech_attendance.model.MonthReportItem;
import com.mezyapps.jmdinfotech_attendance.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MonthlyReportsAdapter  extends RecyclerView.Adapter<MonthlyReportsAdapter.MontnalyReportHolder> {

    Context context;
    List<MonthReportItem> monthlyReportsResponses;

    public MonthlyReportsAdapter(Context context, List<MonthReportItem> monthlyReportsResponseList) {
        this.monthlyReportsResponses = monthlyReportsResponseList;
        this.context = context;
    }

    @Override
    public MonthlyReportsAdapter.MontnalyReportHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.monthly_reports_grid_item, null);
        MonthlyReportsAdapter.MontnalyReportHolder memberViewHolder = new MonthlyReportsAdapter.MontnalyReportHolder(view);
        return memberViewHolder;
    }

    @Override
    public void onBindViewHolder(final MonthlyReportsAdapter.MontnalyReportHolder holder, final int position) {
        // set the data
        holder.textView_date_re.setText(monthlyReportsResponses.get(position).getLogindate());
        holder.textView_in_re.setText(monthlyReportsResponses.get(position).getIn_time());
        holder.textView_out_re.setText(monthlyReportsResponses.get(position).getOut_time());
        holder.work_minut_report.setText(monthlyReportsResponses.get(position).getWork_time());
        holder.textView_add_minut_re.setText(monthlyReportsResponses.get(position).getAdd_minut());
        holder.textView_less_minut_re.setText(monthlyReportsResponses.get(position).getLess_minut());
        holder.textView_netminut_re.setText(monthlyReportsResponses.get(position).getNet_minut());

        SimpleDateFormat format1=new SimpleDateFormat("dd-MM-yyyy");
        Date dt1= null;
        try {
            dt1 = format1.parse(monthlyReportsResponses.get(position).getLogindate());
            DateFormat format2=new SimpleDateFormat("EEE");
            String finalDay=format2.format(dt1);
            if (finalDay.equals("Sun")){
                holder.monthly_report_recucler_itm_layout.setBackgroundColor(context.getResources().getColor(R.color.yellow));
            }
            holder.login_day_report.setText(finalDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (monthlyReportsResponses.get(position).getStatus().equals("S")){
            holder.monthly_report_recucler_itm_layout.setBackgroundColor(context.getResources().getColor(R.color.green));
            holder.status_recyacler_view_month.setText("Status: Sick Leave");
        }
        if (monthlyReportsResponses.get(position).getStatus().equals("P")){
            holder.monthly_report_recucler_itm_layout.setBackgroundColor(context.getResources().getColor(R.color.green));
            holder.status_recyacler_view_month.setText("Status: Paid Leave");
        }
        if (monthlyReportsResponses.get(position).getStatus().equals("C")){
            holder.monthly_report_recucler_itm_layout.setBackgroundColor(context.getResources().getColor(R.color.green));
            holder.status_recyacler_view_month.setText("Status: Casual Leave");
        }
        if (monthlyReportsResponses.get(position).getStatus().equals("E")){
            holder.monthly_report_recucler_itm_layout.setBackgroundColor(context.getResources().getColor(R.color.red));
            holder.status_recyacler_view_month.setText("Status: Extra Leave");
        }
        if (monthlyReportsResponses.get(position).getStatus().equals("LM")){
            holder.monthly_report_recucler_itm_layout.setBackgroundColor(context.getResources().getColor(R.color.lightblue));
            holder.status_recyacler_view_month.setText("Status: Late Mark");
        }
        if (monthlyReportsResponses.get(position).getStatus().equals("EM")){
            holder.monthly_report_recucler_itm_layout.setBackgroundColor(context.getResources().getColor(R.color.blue));
            holder.status_recyacler_view_month.setText("Status: Emergency");
        }
        if (monthlyReportsResponses.get(position).getStatus().equals("HM")){
            holder.monthly_report_recucler_itm_layout.setBackgroundColor(context.getResources().getColor(R.color.lightred));
            holder.status_recyacler_view_month.setText("Status: Half Morning");
        }
        if (monthlyReportsResponses.get(position).getStatus().equals("AB")){
            holder.monthly_report_recucler_itm_layout.setBackgroundColor(context.getResources().getColor(R.color.lightred));
            holder.status_recyacler_view_month.setText("Status: Absent");
        }
        if (monthlyReportsResponses.get(position).getStatus().equals("PH")){
            holder.monthly_report_recucler_itm_layout.setBackgroundColor(context.getResources().getColor(R.color.green));
            holder.status_recyacler_view_month.setText("Status: Holiday");
        }
    }

    @Override
    public int getItemCount() {
        return monthlyReportsResponses.size(); // size of the list items
    }

    class MontnalyReportHolder extends RecyclerView.ViewHolder {
        // init the item view's
        private TextView work_minut_report,textView_date_re,textView_in_re,textView_out_re,textView_add_minut_re,
                textView_less_minut_re,textView_netminut_re,login_day_report,status_recyacler_view_month;
        private  LinearLayout monthly_report_recucler_itm_layout;
        public MontnalyReportHolder(View itemView) {
            super(itemView);
            // get the reference of item view's
            textView_date_re =itemView.findViewById(R.id.login_date_report);
            textView_in_re =itemView.findViewById(R.id.login_time_report);
            textView_out_re =itemView.findViewById(R.id.logout_time_report);
            work_minut_report =itemView.findViewById(R.id.work_minut_report);
            textView_add_minut_re =itemView.findViewById(R.id.add_minutes_report);
            textView_less_minut_re=itemView.findViewById(R.id.less_minutes_report);
            textView_netminut_re=itemView.findViewById(R.id.net_minutes_report);
            login_day_report=itemView.findViewById(R.id.login_day_report);
            status_recyacler_view_month=itemView.findViewById(R.id.status_recyacler_view_month);
            monthly_report_recucler_itm_layout=itemView.findViewById(R.id.monthly_report_recucler_itm_layout);
         
        }
    }

   
}
