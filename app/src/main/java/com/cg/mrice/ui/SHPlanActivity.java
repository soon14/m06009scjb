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
import com.cg.mrice.adapter.PKSHBeanAdapter;
import com.cg.mrice.adapter.PKZHBeanAdapter;
import com.cg.mrice.http.VolleyCallBack;
import com.cg.mrice.http.VolleyTool;
import com.cg.mrice.http.VolleyUtils;
import com.cg.mrice.model.PKSHBean;
import com.cg.mrice.model.PKZHBean;

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

public class SHPlanActivity extends AppCompatActivity implements VolleyCallBack {

    private String url = "https://www.78977a.com/kill-pk10-0.html";
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public List<PKSHBean> lists = new ArrayList<>();
    private StringRequest stringRequest = null;

    @BindView(R.id.rv)
    RecyclerView recyclerView;
    private PKSHBeanAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk_sh);
        ButterKnife.bind(this);
        toolbar.setTitle("专家杀号");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter = new PKSHBeanAdapter(R.layout.layout_item_sh, lists);
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
            stringRequest.setTag(SHPlanActivity.class.getSimpleName());
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
                        List<PKSHBean> list = getData(s);
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

    public List<PKSHBean> getData(String html) throws Exception {
        //获取的数据，存放在集合中
        List<PKSHBean> data = new ArrayList<PKSHBean>();
        //采用Jsoup解析
        Document doc = Jsoup.parse(html);
        //获取html标签中的内容
        Elements elements = doc.select("tbody").select("tr");
        for (Element ele : elements) {
            Elements tds = ele.select("td");
            String time = tds.get(0).text();
            String sfa = tds.get(2).text();
            String jga = tds.get(3).text();
            String sfb = tds.get(4).text();
            String jgb = tds.get(5).text();
            String sfc = tds.get(6).text();
            String jgc = tds.get(7).text();

            PKSHBean pkBean = new PKSHBean();
            pkBean.setQh(time);
            pkBean.setSga(sfa);
            pkBean.setJga(jga);
            pkBean.setSgb(sfb);
            pkBean.setJgb(jgb);
            pkBean.setSgc(sfc);
            pkBean.setJgc(jgc);
            data.add(pkBean);
        }
        //返回数据
        return data;
    }

    @Override
    protected void onDestroy() {
        if (stringRequest != null) {
            VolleyTool.getInstance(this).release(ZHPlanActivity.class.getSimpleName());
        }
        super.onDestroy();
    }
}
