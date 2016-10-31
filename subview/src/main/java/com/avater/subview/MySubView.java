package com.avater.subview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Avater on 2016/10/27 0027.
 */

public class MySubView extends LinearLayout implements View.OnClickListener {

    private Button btn_sub;
    private Button btn_add;
    private TextView tv_size;

    private int value;
    private int minValue;
    private int maxValue;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public MySubView(Context context) {
        this(context, null);
    }

    public MySubView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View.inflate(context, R.layout.subview, this);
        btn_sub = (Button) findViewById(R.id.btn_sub);
        btn_add = (Button) findViewById(R.id.btn_add);
        tv_size = (TextView) findViewById(R.id.tv_size);

        TypedArray typedArray = context.obtainStyledAttributes(attrs,R.styleable.MySubView);

        int count = typedArray.getIndexCount();
        for (int i = 0;i<count;i++){
            int id = typedArray.getIndex(i);
            switch (id){
                case R.styleable.MySubView_value:
                    int mvalue = typedArray.getInt(R.styleable.MySubView_value,1);
                    setValue(mvalue);
                    tv_size.setText(value+"");
                    break;
                case R.styleable.MySubView_minValue:
                    int mMinValue = typedArray.getInt(R.styleable.MySubView_minValue,2);
                    setMinValue(mMinValue);
                    break;
                case R.styleable.MySubView_maxValue:
                    int mMaxValue = typedArray.getInt(R.styleable.MySubView_maxValue,10);
                    setMaxValue(mMaxValue);
                    break;
                case R.styleable.MySubView_bg:
                    int bg_id = typedArray.getResourceId(R.styleable.MySubView_bg,-1);
                    this.setBackgroundResource(bg_id);
                    break;
            }
        }
        typedArray.recycle();
        btn_add.setOnClickListener(this);
        btn_sub.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
//        String value = tv_size.getText().toString();
//        int size = Integer.parseInt(value);
        switch (v.getId()) {
            case R.id.btn_add:
                if (value > maxValue) {
                    return;
                }
                if (value < maxValue) {
                    if (onButtonClickListener != null) {
                        value++;
                        tv_size.setText(value + "");
                        onButtonClickListener.onButtonAddClick(v, value);
                    }
                }
                break;
            case R.id.btn_sub:
                if (value < minValue) {
                    return;
                }
                if (value > minValue) {
                    if (onButtonClickListener != null) {
                        value--;
                        tv_size.setText(value + "");
                        onButtonClickListener.onButtonSubClick(v, value);
                    }
                }

                break;
        }
    }

    public interface OnButtonClickListener {
        void onButtonSubClick(View view, int value);

        void onButtonAddClick(View view, int value);
    }

    private OnButtonClickListener onButtonClickListener;

    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener) {
        this.onButtonClickListener = onButtonClickListener;
    }
}
