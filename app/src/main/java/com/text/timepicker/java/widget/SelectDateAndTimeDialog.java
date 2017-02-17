package com.text.timepicker.java.widget;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.lihui.timepicker.R;


public class SelectDateAndTimeDialog extends Dialog implements View.OnClickListener{

	private TextView date;
	private TextView time;
	private DatePicker   dpTest;
	private TimePicker   tpTest;
	private TextView btn_ok;
	private String getDate;
	private String getTime;
	private String getMonth;
	private String getDay;
	private String getHour;
	
	public interface OnQuickOptionformClick {
        void onQuickOptionClick(int id);
        void getDateAndTime(String getDate, String getTime);
		void getTime(String getMonth, String getDay, String getHour);
    }
	
	private OnQuickOptionformClick mListener;
	
	public SelectDateAndTimeDialog(Context context) {
        this(context, R.style.quick_option_dialog);
    }
	
	private SelectDateAndTimeDialog(Context context, boolean flag, OnCancelListener listener) {
        super(context, flag, listener);
    }
	
	@SuppressLint("ClickableViewAccessibility")
	private SelectDateAndTimeDialog(Context context, int defStyle) {
        super(context, defStyle);
        View contentView = getLayoutInflater().inflate(R.layout.custom_dialog_time_and_date, null);
        dpTest = (DatePicker) contentView.findViewById(R.id.dp_test);
        tpTest = (TimePicker) contentView.findViewById(R.id.tp_test);
        date = (TextView) contentView.findViewById(R.id.lbl_date);
        time = (TextView) contentView.findViewById(R.id.lbl_time);
        btn_ok = (TextView) contentView.findViewById(R.id.btn_ok);
        dpTest.setOnChangeListener(dp_onchanghelistener);
        tpTest.setOnChangeListener(tp_onchanghelistener);
        btn_ok.setOnClickListener(this);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        contentView.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				SelectDateAndTimeDialog.this.dismiss();
				return true;
			}
        });
        super.setContentView(contentView);

    }

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setGravity(Gravity.BOTTOM);

        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        p.width = d.getWidth();
        getWindow().setAttributes(p);
	}
	
	public void setOnQuickOptionformClickListener(OnQuickOptionformClick lis) {
        mListener = lis;
    }
	
	DatePicker.OnChangeListener dp_onchanghelistener = new DatePicker.OnChangeListener() {
		@Override
		public void onChange(int year, int month, int day, int day_of_week) {
//			date.setText(year + "年" + month + "月" + day + "日" );
			date.setText(year + "年" + (month<(10)?("0"+month):month) + "月" + ((day<10)?("0"+day):day) + "日" );
			getDate = year + "-" + (month<(10)?("0"+month):month) + "-" + ((day<10)?("0"+day):day)  ;
//			getMonth = year+"";
//			getDay = month+"";
//			getHour = day+"";

		}
	};
	TimePicker.OnChangeListener tp_onchanghelistener = new TimePicker.OnChangeListener() {
		@Override
		public void onChange(int hour, int minute) {
			time.setText(((hour < 10)?("0"+hour):hour) + ":" + ((minute < 10)?("0"+minute):minute));
			String mi = "";
			mi = minute < 10 ? ("0"+minute) : minute+"";
			getTime = ((hour < 10)?("0"+hour):hour) + ":" + ((minute < 10)?("0"+minute):minute);
		}
	};
	
	public String getDateAndTime(){
		return getDate + " " + getTime;
	}
	public String getTime(){
		return getMonth + getDay + getHour;
	}


	@Override
	public void onClick(View v) {
		final int id = v.getId();
		switch (id) {
		case R.id.btn_ok:
			dismiss();
			break;
		default:
			break;
		}
		if (mListener != null) {
			mListener.onQuickOptionClick(id);
			mListener.getDateAndTime(getDate ,getTime);
			mListener.getTime(getMonth, getDay, getHour);
		}
		dismiss();
	}
}
