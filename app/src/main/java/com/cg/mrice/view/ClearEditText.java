package com.cg.mrice.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.cg.mrice.R;

/**
 * Created by Vinchaos api on 13-11-24.
 */
@SuppressLint("AppCompatCustomView")
public class ClearEditText extends EditText implements
        View.OnFocusChangeListener, TextWatcher {

    private Drawable mClearDrawable;
    /**判断焦点*/
    private boolean hasFocus;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null)
            mClearDrawable = getResources().getDrawable(R.drawable.edit_clear_selector);//NullPointerException ,在XML中设置drawableRight

        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }


    /**
     * 模拟记录点击位置，来设置点击事件
     */
    /**
     * 当手指抬起的位置在clean的图标的区域
     * 我们将此视为进行清除操作
     * getWidth():得到控件的宽度
     * event.getX():抬起时的坐标(改坐标是相对于控件本身而言的)
     * getTotalPaddingRight():clean的图标左边缘至控件右边缘的距离
     * getPaddingRight():clean的图标右边缘至控件右边缘的距离
     * 于是:
     * getWidth() - getTotalPaddingRight()表示:
     * 控件左边到clean的图标左边缘的区域
     * getWidth() - getPaddingRight()表示:
     * 控件左边到clean的图标右边缘的区域
     * 所以这两者之间的区域刚好是clean的图标的区域
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            clearText(event);
        }

        return super.onTouchEvent(event);
    }
    public void clearText(MotionEvent event){
        if (getCompoundDrawables()[2] != null) {

            //增大点范围进行测试
            boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight()-10)
                    && (event.getX() < ((getWidth() - getPaddingRight()+10)));

            if (touchable) {
                this.setText("");
            }
        }
    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFocus = hasFocus;

        //1 去掉所有输入框全清键
//        if (hasFocus) {
//            setClearIconVisible(getText().length() > 0);
//        } else {
//            setClearIconVisible(false);
//        }
    }


    /**
     * 设置显隐
     *
     * @param visible
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }


    @Override
    public void onTextChanged(CharSequence s, int start, int count,
                              int after) {
        //2 去掉所有输入框全清键
//        if (hasFocus) {
//            setClearIconVisible(s.length() > 0);
//        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

}