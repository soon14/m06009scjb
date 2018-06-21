package com.cg.mrice.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cg.mrice.R;
import com.cg.mrice.adapter.PKZSBeanAdapter;
import com.cg.mrice.http.VolleyCallBack;
import com.cg.mrice.http.VolleyTool;
import com.cg.mrice.http.VolleyUtils;
import com.cg.mrice.model.PKZSBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr on 2018/6/4.
 */

public class ZSPKActivity extends AppCompatActivity implements VolleyCallBack {

    private String url = "https://www.pk106.com/trend-pk10-basic-today-0.html";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public List<PKZSBean> lists = new ArrayList<>();
    private StringRequest stringRequest = null;

    @BindView(R.id.rv)
    RecyclerView recyclerView;
    private PKZSBeanAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk_zs);
        ButterKnife.bind(this);
        toolbar.setTitle("露珠分析");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter = new PKZSBeanAdapter(R.layout.layout_item_zs, lists);
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        initPK10();
    }

    private void initPK10() {
        stringRequest = new VolleyUtils(2000, this).getStringRequest(url);
        if (stringRequest != null) {
            stringRequest.setTag(ZSPKActivity.class.getSimpleName());
            VolleyTool.getInstance(this).getmRequestQueue().add(stringRequest);
        }
    }

    @Override
    public void onResponse(String s, int tag) {
        if (isFinishing()) {
            return;
        }
        switch (tag) {
            case 2000:

                try {
                    if (!TextUtils.isEmpty(s)) {
                        List<PKZSBean> list = getData(s);
                        if (list != null && list.size() > 0) {
                            lists.addAll(list);
                            adapter.notifyDataSetChanged();
                        } else {

                        }
                    } else {

                    }
                } catch (Exception ex) {

                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError, int tag) {

    }

    @Override
    public void onFailedResponse(String s) {

    }

    public List<PKZSBean> getData(String html) throws Exception {
        //获取的数据，存放在集合中
        List<PKZSBean> data = new ArrayList<PKZSBean>();
        //采用Jsoup解析
        Document doc = Jsoup.parse(html);
        //获取html标签中的内容
        Elements elements = doc.select("table[id=trend-table]").select("tbody").select("tr");
        for (Element ele : elements) {
            Elements tds = ele.select("td");
            String time = tds.get(0).text();
            String fx = tds.get(11).text();
            String ch = tds.get(12).text();
            String zx = tds.get(13).text();
            String dan = tds.get(14).text();
            String shuang = tds.get(15).text();
            String da = tds.get(16).text();
            String xiao = tds.get(17).text();

            PKZSBean pkBean = new PKZSBean();
            pkBean.setQh(time);
            pkBean.setFx(fx);
            pkBean.setCh(ch);
            pkBean.setZx(zx);
            pkBean.setDan(dan);
            pkBean.setShuang(shuang);
            pkBean.setDa(da);
            pkBean.setXiao(xiao);
            data.add(pkBean);
        }
        //返回数据
        return data;
    }

    @Override
    protected void onDestroy() {
        if (stringRequest != null) {
            VolleyTool.getInstance(this).release(ZSPKActivity.class.getSimpleName());
        }
        super.onDestroy();
    }
}
