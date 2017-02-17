package com.text.timepicker.java.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;


import com.example.lihui.timepicker.R;

import java.util.Calendar;

/**
 * DatePicker
 * @author lh
 * @date 2017/02/17
 */
public class DatePicker extends LinearLayout {
	
	private Calendar calendar = Calendar.getInstance(); //������
	private WheelView years, months, days; //Wheel picker
	private OnChangeListener onChangeListener; //onChangeListener
	
	//Constructors
	public DatePicker(Context context) {
		super(context);
		init(context);
	}
	
	public DatePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * ��ʼ�����
	 * @param context
	 */
	private void init(Context context){
		int width = context.getResources().getDimensionPixelSize(R.dimen.date_time_picker_obj_width);
		
		years = new WheelView(context);
		LayoutParams lparams_hours = new LayoutParams(width, LayoutParams.WRAP_CONTENT);
		lparams_hours.setMargins(0, 0, 1, 0);
		years.setLayoutParams(lparams_hours);
		years.setAdapter(new NumericWheelAdapter(1900, 2100));
		years.setVisibleItems(5);
		years.addChangingListener(onYearsChangedListener);		
		addView(years);		
		
		months = new WheelView(context);
		LayoutParams lparams_month = new LayoutParams(width, LayoutParams.WRAP_CONTENT);
		lparams_month.setMargins(0, 0, 1, 0);
		months.setLayoutParams(lparams_month);
		months.setAdapter(new NumericWheelAdapter(1, 12, "%02d"));
		months.setVisibleItems(5);
		months.setCyclic(true);
		months.addChangingListener(onMonthsChangedListener);		
		addView(months);		
		
		days = new WheelView(context);
		days.setLayoutParams(new LayoutParams(width, LayoutParams.WRAP_CONTENT));
		int maxday_of_month = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		days.setAdapter(new NumericWheelAdapter(1, maxday_of_month, "%02d"));
		days.setVisibleItems(5);
		days.setCyclic(true);
		days.addChangingListener(onDaysChangedListener);		
		addView(days);
	}
	
	//listeners
	private OnWheelChangedListener onYearsChangedListener = new OnWheelChangedListener(){
		@Override
		public void onChanged(WheelView hours, int oldValue, int newValue) {
			calendar.set(Calendar.YEAR, 1900 + newValue);
			onChangeListener.onChange(getYear(), getMonth(), getDay(), getDayOfWeek());	
			setMonth(getMonth());
			setDay(getDay());
		}
	};
	private OnWheelChangedListener onMonthsChangedListener = new OnWheelChangedListener(){
		@Override
		public void onChanged(WheelView mins, int oldValue, int newValue) {
			calendar.set(Calendar.MONTH, 1 + newValue - 1);
			onChangeListener.onChange(getYear(), getMonth(), getDay(), getDayOfWeek());		
			setMonth(getMonth());
			setDay(getDay());
		}
	};
	private OnWheelChangedListener onDaysChangedListener = new OnWheelChangedListener(){
		@Override
		public void onChanged(WheelView mins, int oldValue, int newValue) {
			calendar.set(Calendar.DAY_OF_MONTH, newValue + 1);
			onChangeListener.onChange(getYear(), getMonth(), getDay(), getDayOfWeek());
			days.setAdapter(new NumericWheelAdapter(1, calendar.getActualMaximum(Calendar.DAY_OF_MONTH), "%02d"));
		}
	};
	
	
	/**
	 * �����˼���ʱ��ı�ļ��������
	 * @author Wang_Yuliang
	 *
	 */
	public interface OnChangeListener {
		void onChange(int year, int month, int day, int day_of_week);
	}
	
	/**
	 * ���ü������ķ���
	 * @param onChangeListener
	 */
	public void setOnChangeListener(OnChangeListener onChangeListener){
		this.onChangeListener = onChangeListener;
	}
	
	/**
	 * ������
	 * @param year
	 */
	public void setYear(int year){
		years.setCurrentItem(year-1900);
	}
	
	/**
	 * �����
	 * @return
	 */
	public int getYear(){
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	 * ������
	 */
	public void setMonth(int month){
		months.setCurrentItem(month - 1);
	}
	
	/**
	 * �����
	 * @return
	 */
	public int getMonth(){
		return calendar.get(Calendar.MONTH)+1;
	}
	
	/**
	 * ������
	 * @param day
	 */
	public void setDay(int day){
		days.setCurrentItem(day - 1);
	}
	
	/**
	 * �����
	 * @return
	 */
	public int getDay(){
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * �������
	 * @return
	 */
	public int getDayOfWeek(){
		return calendar.get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * �������
	 * @return
	 */
	public static String getDayOfWeekCN(int day_of_week){
		String result = null;
		switch(day_of_week){
		case 1:
			result = "星期一";
			break;
		case 2:
			result = "星期二";
			break;
		case 3:
			result = "星期三";
			break;
		case 4:
			result = "星期四";
			break;
		case 5:
			result = "星期五";
			break;
		case 6:
			result = "星期六";
			break;
		case 7:
			result = "星期日";
			break;	
		default:
			break;
		}
		return result;
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//Ĭ������Ϊϵͳʱ��
		setYear(getYear());
		setMonth(getMonth());
		setDay(getDay());
	}
}
