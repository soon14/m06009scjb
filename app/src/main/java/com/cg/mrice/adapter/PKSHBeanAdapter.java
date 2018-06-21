package com.cg.mrice.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.cg.mrice.R;
import com.cg.mrice.model.PKSHBean;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Mr on 2018/6/4.
 */

public class PKSHBeanAdapter extends BaseQuickAdapter<PKSHBean, BaseViewHolder> {


    public PKSHBeanAdapter(int layoutResId, @Nullable List<PKSHBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PKSHBean item) {
        helper.setText(R.id.qh, item.getQh()).setText(R.id.sfa, item.getSga()).setText(R.id.jga, item.getJga()).setText(R.id.sfb, item.getSga()).setText(R.id.jgb, item.getJga()).setText(R.id.sfc, item.getSga()).setText(R.id.jgc, item.getJga());
    }
}
