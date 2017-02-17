package com.text.timepicker.java;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.lihui.timepicker.R;
import com.text.timepicker.java.widget.SelectDateAndTimeDialog;
import com.text.timepicker.java.utils.DateUtil;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private LinearLayout ll_select_start_time;
    private LinearLayout ll_select_end_time;
    private TextView et_start_time;
    private TextView et_end_time;
    private int clickType = 0 ;//0:开始时间 1:结束时间
    private String startTime;
    private String endTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();

    }

    private void initView() {
        ll_select_start_time = (LinearLayout) findViewById(R.id.ll_select_start_time);
        ll_select_end_time = (LinearLayout) findViewById(R.id.ll_select_end_time);
        et_start_time = (TextView) findViewById(R.id.et_start_time);
        et_end_time = (TextView) findViewById(R.id.et_end_time);
        ll_select_start_time.setOnClickListener(this);
        ll_select_end_time.setOnClickListener(this);
    }

    private void initData() {
        et_start_time.setText(DateUtil.getStringDate());
        et_end_time.setText(DateUtil.getStringDate());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_select_start_time:
                clickType=0;
                final SelectDateAndTimeDialog dialogStart = new SelectDateAndTimeDialog(MainActivity.this);
                dialogStart.setCancelable(false);
                dialogStart.setCanceledOnTouchOutside(true);
                dialogStart.setOnQuickOptionformClickListener(new GetNewDateTimeListener());
                dialogStart.show();
                break;
            case R.id.ll_select_end_time:
                clickType=1;
                final SelectDateAndTimeDialog dialogEnd = new SelectDateAndTimeDialog(MainActivity.this);
                dialogEnd.setCancelable(false);
                dialogEnd.setCanceledOnTouchOutside(true);
                dialogEnd.setOnQuickOptionformClickListener(new GetNewDateTimeListener());
                dialogEnd.show();
                break;
            default:
                break;
        }
    }
    class GetNewDateTimeListener implements SelectDateAndTimeDialog.OnQuickOptionformClick{
        @Override
        public void onQuickOptionClick(int id) {

        }

        @Override
        public void getTime(String getMonth, String getDay, String getHour) {

        }

        @Override
        public void getDateAndTime(String getDate, String getTime) {

         if(0 == clickType){
             startTime = getDate +" "+getTime;
             if(null != startTime && !"" .equals(startTime)){
                 et_start_time.setText(startTime);
             } else {
                 Toast.makeText(MainActivity.this , " 请选择开始时间", Toast.LENGTH_SHORT).show();
             }

         }else{
             endTime = getDate + " " + getTime;
             if(null != endTime && !"".equals(endTime)) {
                 et_end_time.setText(endTime);
             }else{
                 Toast.makeText(MainActivity.this , " 请选择结束时间", Toast.LENGTH_SHORT).show();

             }


         }
            clickType =0;
        }
    }

}
