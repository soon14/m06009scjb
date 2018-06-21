package com.cg.mrice.adapter;

import android.support.annotation.Nullable;

import com.cg.mrice.R;
import com.cg.mrice.model.PKBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Mr on 2018/6/3.
 */

public class PKBeanAdapter extends BaseQuickAdapter<PKBean, BaseViewHolder> {

    private int type = 0;

    public PKBeanAdapter(int layoutResId, @Nullable List<PKBean> data) {
        super(layoutResId, data);
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, PKBean item) {
        String[] titles = item.getTime().split(" ");
        helper.setText(R.id.pkTime, "开奖时间 " + titles[1]).setText(R.id.pkQh, "期号 " + titles[0]);
        helper.setText(R.id.gyh1, item.getGyh1()).setText(R.id.gyh2, item.getGyh2()).setText(R.id.gyh3, item.getGyh3());
        String[] arrs = item.getStrNo().split("-");
        if (type == 0) {
            if (arrs.length == 10) {
                helper.setText(R.id.p1, arrs[0]);
                helper.setText(R.id.p2, arrs[1]);
                helper.setText(R.id.p3, arrs[2]);
                helper.setText(R.id.p4, arrs[3]);
                helper.setText(R.id.p5, arrs[4]);
                helper.setText(R.id.p6, arrs[5]);
                helper.setText(R.id.p7, arrs[6]);
                helper.setText(R.id.p8, arrs[7]);
                helper.setText(R.id.p9, arrs[8]);
                helper.setText(R.id.p10, arrs[9]);
                helper.setBackgroundRes(R.id.p1, getBackGround(arrs[0]));
                helper.setBackgroundRes(R.id.p2, getBackGround(arrs[1]));
                helper.setBackgroundRes(R.id.p3, getBackGround(arrs[2]));
                helper.setBackgroundRes(R.id.p4, getBackGround(arrs[3]));
                helper.setBackgroundRes(R.id.p5, getBackGround(arrs[4]));
                helper.setBackgroundRes(R.id.p6, getBackGround(arrs[5]));
                helper.setBackgroundRes(R.id.p7, getBackGround(arrs[6]));
                helper.setBackgroundRes(R.id.p8, getBackGround(arrs[7]));
                helper.setBackgroundRes(R.id.p9, getBackGround(arrs[8]));
                helper.setBackgroundRes(R.id.p10, getBackGround(arrs[9]));
            } else {
                helper.setText(R.id.p1, "1");
                helper.setText(R.id.p2, "2");
                helper.setText(R.id.p3, "3");
                helper.setText(R.id.p4, "4");
                helper.setText(R.id.p5, "5");
                helper.setText(R.id.p6, "6");
                helper.setText(R.id.p7, "7");
                helper.setText(R.id.p8, "8");
                helper.setText(R.id.p9, "9");
                helper.setText(R.id.p10, "10");
                helper.setBackgroundRes(R.id.p1, R.drawable.pk1);
                helper.setBackgroundRes(R.id.p2, R.drawable.pk2);
                helper.setBackgroundRes(R.id.p3, R.drawable.pk3);
                helper.setBackgroundRes(R.id.p4, R.drawable.pk4);
                helper.setBackgroundRes(R.id.p5, R.drawable.pk5);
                helper.setBackgroundRes(R.id.p6, R.drawable.pk6);
                helper.setBackgroundRes(R.id.p7, R.drawable.pk7);
                helper.setBackgroundRes(R.id.p8, R.drawable.pk8);
                helper.setBackgroundRes(R.id.p9, R.drawable.pk9);
                helper.setBackgroundRes(R.id.p10, R.drawable.pk10);
            }
        } else if (type == 1) {
            if (arrs.length == 10) {
                helper.setText(R.id.p1, getBigSmall(arrs[0]));
                helper.setText(R.id.p2, getBigSmall(arrs[1]));
                helper.setText(R.id.p3, getBigSmall(arrs[2]));
                helper.setText(R.id.p4, getBigSmall(arrs[3]));
                helper.setText(R.id.p5, getBigSmall(arrs[4]));
                helper.setText(R.id.p6, getBigSmall(arrs[5]));
                helper.setText(R.id.p7, getBigSmall(arrs[6]));
                helper.setText(R.id.p8, getBigSmall(arrs[7]));
                helper.setText(R.id.p9, getBigSmall(arrs[8]));
                helper.setText(R.id.p10, getBigSmall(arrs[9]));
                helper.setBackgroundRes(R.id.p1, getBSBackGround(arrs[0]));
                helper.setBackgroundRes(R.id.p2, getBSBackGround(arrs[1]));
                helper.setBackgroundRes(R.id.p3, getBSBackGround(arrs[2]));
                helper.setBackgroundRes(R.id.p4, getBSBackGround(arrs[3]));
                helper.setBackgroundRes(R.id.p5, getBSBackGround(arrs[4]));
                helper.setBackgroundRes(R.id.p6, getBSBackGround(arrs[5]));
                helper.setBackgroundRes(R.id.p7, getBSBackGround(arrs[6]));
                helper.setBackgroundRes(R.id.p8, getBSBackGround(arrs[7]));
                helper.setBackgroundRes(R.id.p9, getBSBackGround(arrs[8]));
                helper.setBackgroundRes(R.id.p10, getBSBackGround(arrs[9]));
            } else {
                helper.setText(R.id.p1, "小");
                helper.setText(R.id.p2, "小");
                helper.setText(R.id.p3, "小");
                helper.setText(R.id.p4, "小");
                helper.setText(R.id.p5, "小");
                helper.setText(R.id.p6, "大");
                helper.setText(R.id.p7, "大");
                helper.setText(R.id.p8, "大");
                helper.setText(R.id.p9, "大");
                helper.setText(R.id.p10, "大");
                helper.setBackgroundRes(R.id.p1, R.drawable.pk10);
                helper.setBackgroundRes(R.id.p2, R.drawable.pk10);
                helper.setBackgroundRes(R.id.p3, R.drawable.pk10);
                helper.setBackgroundRes(R.id.p4, R.drawable.pk10);
                helper.setBackgroundRes(R.id.p5, R.drawable.pk10);
                helper.setBackgroundRes(R.id.p6, R.drawable.pk8);
                helper.setBackgroundRes(R.id.p7, R.drawable.pk8);
                helper.setBackgroundRes(R.id.p8, R.drawable.pk8);
                helper.setBackgroundRes(R.id.p9, R.drawable.pk8);
                helper.setBackgroundRes(R.id.p10, R.drawable.pk8);
            }
        } else {
            if (arrs.length == 10) {
                helper.setText(R.id.p1, getDS(arrs[0]));
                helper.setText(R.id.p2, getDS(arrs[1]));
                helper.setText(R.id.p3, getDS(arrs[2]));
                helper.setText(R.id.p4, getDS(arrs[3]));
                helper.setText(R.id.p5, getDS(arrs[4]));
                helper.setText(R.id.p6, getDS(arrs[5]));
                helper.setText(R.id.p7, getDS(arrs[6]));
                helper.setText(R.id.p8, getDS(arrs[7]));
                helper.setText(R.id.p9, getDS(arrs[8]));
                helper.setText(R.id.p10, getDS(arrs[9]));
                helper.setBackgroundRes(R.id.p1, getDSBackGround(arrs[0]));
                helper.setBackgroundRes(R.id.p2, getDSBackGround(arrs[1]));
                helper.setBackgroundRes(R.id.p3, getDSBackGround(arrs[2]));
                helper.setBackgroundRes(R.id.p4, getDSBackGround(arrs[3]));
                helper.setBackgroundRes(R.id.p5, getDSBackGround(arrs[4]));
                helper.setBackgroundRes(R.id.p6, getDSBackGround(arrs[5]));
                helper.setBackgroundRes(R.id.p7, getDSBackGround(arrs[6]));
                helper.setBackgroundRes(R.id.p8, getDSBackGround(arrs[7]));
                helper.setBackgroundRes(R.id.p9, getDSBackGround(arrs[8]));
                helper.setBackgroundRes(R.id.p10, getDSBackGround(arrs[9]));
            } else {
                helper.setText(R.id.p1, "单");
                helper.setText(R.id.p2, "双");
                helper.setText(R.id.p3, "单");
                helper.setText(R.id.p4, "双");
                helper.setText(R.id.p5, "单");
                helper.setText(R.id.p6, "双");
                helper.setText(R.id.p7, "单");
                helper.setText(R.id.p8, "双");
                helper.setText(R.id.p9, "单");
                helper.setText(R.id.p10, "双");
                helper.setBackgroundRes(R.id.p1, R.drawable.pk4);
                helper.setBackgroundRes(R.id.p2, R.drawable.pk6);
                helper.setBackgroundRes(R.id.p3, R.drawable.pk4);
                helper.setBackgroundRes(R.id.p4, R.drawable.pk6);
                helper.setBackgroundRes(R.id.p5, R.drawable.pk4);
                helper.setBackgroundRes(R.id.p6, R.drawable.pk6);
                helper.setBackgroundRes(R.id.p7, R.drawable.pk4);
                helper.setBackgroundRes(R.id.p8, R.drawable.pk6);
                helper.setBackgroundRes(R.id.p9, R.drawable.pk4);
                helper.setBackgroundRes(R.id.p10, R.drawable.pk6);
            }
        }

    }

    private String getBigSmall(String number) {
        if (Integer.parseInt(number) > 5) {
            return "大";
        } else {
            return "小";
        }
    }

    private String getDS(String number) {
        if (Integer.parseInt(number) % 2 == 0) {
            return "双";
        } else {
            return "单";
        }
    }

    private int getBSBackGround(String number) {
        int num = Integer.valueOf(number);
        if (num > 5) {
            return R.drawable.pk8;
        } else {
            return R.drawable.pk10;
        }
    }

    private int getDSBackGround(String number) {
        int num = Integer.valueOf(number);
        if (num % 2 == 0) {
            return R.drawable.pk4;
        } else {
            return R.drawable.pk6;
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
