package com.cg.mrice.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.cg.mrice.R;
import com.cg.mrice.model.PKSHBean;
import com.cg.mrice.model.PKZSBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Mr on 2018/6/4.
 */

public class PKZSBeanAdapter extends BaseQuickAdapter<PKZSBean, BaseViewHolder> {


    public PKZSBeanAdapter(int layoutResId, @Nullable List<PKZSBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PKZSBean item) {
        helper.setText(R.id.qh, item.getQh()).setText(R.id.fx, item.getFx()).setText(R.id.ch, item.getCh()).setText(R.id.zx, item.getZx()).setText(R.id.dan, item.getDan()).setText(R.id.shuang, item.getShuang()).setText(R.id.da, item.getDa()).setText(R.id.xiao, item.getXiao());
        if (item.getFx().contains("反")) {
            helper.setTextColor(R.id.fx, Color.WHITE);
            helper.setBackgroundRes(R.id.fx, R.color.line3);
        } else {
            helper.setTextColor(R.id.fx, Color.BLACK);
            helper.setBackgroundColor(R.id.fx, Color.TRANSPARENT);
        }
        if (item.getCh().contains("重")) {
            helper.setTextColor(R.id.ch, Color.WHITE);
            helper.setBackgroundRes(R.id.ch, R.color.line3);
        } else {
            helper.setTextColor(R.id.ch, Color.BLACK);
            helper.setBackgroundColor(R.id.ch, Color.TRANSPARENT);
        }
        if (item.getZx().contains("正")) {
            helper.setTextColor(R.id.zx, Color.WHITE);
            helper.setBackgroundRes(R.id.zx, R.color.line3);
        } else {
            helper.setTextColor(R.id.zx, Color.BLACK);
            helper.setBackgroundColor(R.id.zx, Color.TRANSPARENT);

        }
        if (item.getDan().contains("单")) {
            helper.setTextColor(R.id.dan, Color.WHITE);
            helper.setBackgroundRes(R.id.dan, R.color.line1);
        } else {
            helper.setTextColor(R.id.dan, Color.BLACK);
            helper.setBackgroundColor(R.id.dan, Color.TRANSPARENT);
        }
        if (item.getShuang().contains("双")) {

            helper.setTextColor(R.id.shuang, Color.WHITE);
            helper.setBackgroundRes(R.id.shuang, R.color.line1);
        } else {
            helper.setTextColor(R.id.shuang, Color.BLACK);
            helper.setBackgroundColor(R.id.shuang, Color.TRANSPARENT);

        }
        if (item.getDa().contains("大")) {
            helper.setTextColor(R.id.da, Color.WHITE);
            helper.setBackgroundRes(R.id.da, R.color.line5);
        } else {
            helper.setTextColor(R.id.da, Color.BLACK);
            helper.setBackgroundColor(R.id.da, Color.TRANSPARENT);
        }
        if (item.getXiao().contains("小")) {
            helper.setTextColor(R.id.xiao, Color.WHITE);
            helper.setBackgroundRes(R.id.xiao, R.color.line5);
        } else {
            helper.setTextColor(R.id.xiao, Color.BLACK);
            helper.setBackgroundColor(R.id.xiao, Color.TRANSPARENT);

        }
    }
}
