package com.text.timepicker.java.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;


import com.example.lihui.timepicker.R;

import java.util.Calendar;


/**
 * TimePicker
 * @author lh
 * @date 2017/02/17
 */
public class TimePicker extends LinearLayout {
	
	private Calendar calendar = Calendar.getInstance(); //������
	private boolean isHourOfDay = true; //24Сʱ��?  Ĭ�� �� �����ڶ���24Сʱ�Ƶ� �Ǹ�12Сʱ��û���أ�
	private WheelView hours, mins; //Wheel picker
	private OnChangeListener onChangeListener; //onChangeListener
	
	//Constructors
	public TimePicker(Context context) {
		super(context);
		init(context);
	}
	
	public TimePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	/**
	 * ��ʼ�����
	 * @param context
	 */
	private void init(Context context){
		int width = context.getResources().getDimensionPixelSize(R.dimen.date_time_picker_obj_width);
		
		hours = new WheelView(context);
		LayoutParams lparams_hours = new LayoutParams(width, LayoutParams.WRAP_CONTENT);
		lparams_hours.setMargins(0, 0, 1, 0);
		hours.setLayoutParams(lparams_hours);
		hours.setAdapter(new NumericWheelAdapter(0, 23));
		hours.setVisibleItems(5);
		hours.addChangingListener(onHoursChangedListener);		
		addView(hours);		
		
		mins = new WheelView(context);
		mins.setLayoutParams(new LayoutParams(width, LayoutParams.WRAP_CONTENT));
		mins.setAdapter(new NumericWheelAdapter(0, 59, "%02d"));
		mins.setVisibleItems(5);
		mins.setCyclic(true);
		mins.addChangingListener(onMinsChangedListener);		
		addView(mins);			
	}
	
	
	
	//listeners
	private OnWheelChangedListener onHoursChangedListener = new OnWheelChangedListener(){
		@Override
		public void onChanged(WheelView hours, int oldValue, int newValue) {
			calendar.set(Calendar.HOUR_OF_DAY, newValue);
			onChangeListener.onChange(getHourOfDay(), getMinute());
		}
	};
	private OnWheelChangedListener onMinsChangedListener = new OnWheelChangedListener(){
		@Override
		public void onChanged(WheelView mins, int oldValue, int newValue) {
			calendar.set(Calendar.MINUTE, newValue);
			onChangeListener.onChange(getHourOfDay(), getMinute());
		}
	};
	
	/**
	 * �����˼���ʱ��ı�ļ��������
	 * @author Wang_Yuliang
	 *
	 */
	public interface OnChangeListener {
		void onChange(int hour, int munite);
	}
	
	/**
	 * ���ü������ķ���
	 * @param onChangeListener
	 */
	public void setOnChangeListener(OnChangeListener onChangeListener){
		this.onChangeListener = onChangeListener;
	}
	
	/**
	 * ����Сʱ
	 * @param hour
	 */
	public void setHourOfDay(int hour){
		hours.setCurrentItem(hour);
	}
	
	/**
	 * ���24Сʱ��Сʱ
	 * @return
	 */
	public int getHourOfDay(){
		return calendar.get(Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * ���÷���
	 */
	public void setMinute(int minute){
		mins.setCurrentItem(minute);
	}
	
	/**
	 * ��÷���
	 * @return
	 */
	public int getMinute(){
		return calendar.get(Calendar.MINUTE);
	}
		
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		//Ĭ������Ϊϵͳʱ��
		setHourOfDay(getHourOfDay());
		setMinute(getMinute());
	}
}
