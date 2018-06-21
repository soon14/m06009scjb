package com.cg.mrice.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.cg.mrice.R;
import com.cg.mrice.model.PKZHBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Mr on 2018/6/3.
 */

public class PKZHBeanAdapter extends BaseQuickAdapter<PKZHBean, BaseViewHolder> {


    public PKZHBeanAdapter(int layoutResId, @Nullable List<PKZHBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PKZHBean item) {
        helper.setText(R.id.qh, item.getQh()).setText(R.id.jg, item.getJg());
        helper.setText(R.id.p1, item.getP1()).setText(R.id.p2, item.getP2()).setText(R.id.p3, item.getP2());
        helper.setBackgroundRes(R.id.p1, getBackGround(item.getP1())).setBackgroundRes(R.id.p2, getBackGround(item.getP2())).setBackgroundRes(R.id.p3, getBackGround(item.getP3()));
        if (item.getJg().contains("当")) {
            helper.setTextColor(R.id.jg, Color.WHITE);
            helper.setBackgroundRes(R.id.jg, R.color.line9);
        } else if (item.getJg().contains("中出")) {
            helper.setTextColor(R.id.jg, Color.WHITE);
            helper.setBackgroundRes(R.id.jg, R.color.line3);
        } else {
            helper.setTextColor(R.id.jg, Color.BLACK);
            helper.setBackgroundColor(R.id.jg, Color.TRANSPARENT);
        }
    }

    private int getBackGround(String number) {
        int num = Integer.valueOf(number);
        int res = 0;
        switch (num) {
            case 1:
                res = R.drawable.pk1;
                break;
            case 2:
                res = R.drawable.pk2;
                break;
            case 3:
                res = R.drawable.pk3;
                break;
            case 4:
                res = R.drawable.pk4;
                break;
            case 5:
                res = R.drawable.pk5;
                break;
            case 6:
                res = R.drawable.pk6;
                break;
            case 7:
                res = R.drawable.pk7;
                break;
            case 8:
                res = R.drawable.pk8;
                break;
            case 9:
                res = R.drawable.pk9;
                break;
            case 10:
                res = R.drawable.pk10;
                break;
        }
        return res;
    }
}
