package com.cg.mrice.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cg.mrice.PanActivity;
import com.cg.mrice.R;
import com.cg.mrice.adapter.PKBeanAdapter;
import com.cg.mrice.data.CommonData;
import com.cg.mrice.http.VolleyCallBack;
import com.cg.mrice.http.VolleyTool;
import com.cg.mrice.http.VolleyUtils;
import com.cg.mrice.model.LotteryBean;
import com.cg.mrice.model.PKBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr on 2018/6/3.
 */

public class PKHistoryActivity extends AppCompatActivity implements VolleyCallBack {

    private String pk_url = "https://www.pk106.com/draw-pk10-today.html";

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static List<PKBean> lists = new ArrayList<>();
    private StringRequest stringRequest = null;

    @BindView(R.id.rv)
    RecyclerView recyclerView;
    private PKBeanAdapter adapter;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pk_history);
        ButterKnife.bind(this);
        toolbar.setTitle("历史开奖");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        adapter = new PKBeanAdapter(R.layout.layout_item_pk10, lists);
        //创建布局管理
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        tabLayout.addTab(tabLayout.newTab().setText("显示号码"));
        tabLayout.addTab(tabLayout.newTab().setText("显示大小"));
        tabLayout.addTab(tabLayout.newTab().setText("显示单双"));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        adapter.setType(0);
                        adapter.notifyDataSetChanged();
                        break;
                    case 1:
                        adapter.setType(1);
                        adapter.notifyDataSetChanged();
                        break;
                    case 2:
                        adapter.setType(2);
                        adapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        initPK10();
    }

    private void initPK10() {
        stringRequest = new VolleyUtils(2000, this).getStringRequest(pk_url);
        if (stringRequest != null) {
            stringRequest.setTag(PKHistoryActivity.class.getSimpleName());
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
                        List<PKBean> list = getData(s);
                        if (list != null && list.size() > 0) {
                            lists.addAll(list);
                            adapter.notifyDataSetChanged();
                        } else {
                            lists.addAll(getData(CommonData.PK10_ALL));
                            adapter.notifyDataSetChanged();
                        }
                    } else {
                        lists.addAll(getData(CommonData.PK10_ALL));
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception ex) {

                }
                break;
            default:
                break;
        }
    }

    public List<PKBean> getData(String html) throws Exception {
        //获取的数据，存放在集合中
        List<PKBean> data = new ArrayList<PKBean>();
        //采用Jsoup解析
        Document doc = Jsoup.parse(html);
        //获取html标签中的内容
        Elements elements = doc.select("table[id=table-pk10]").select("tr[class=tr]");
        for (Element ele : elements) {
            Elements tds = ele.select("td");
            String time = tds.get(0).text();
            Elements spans = tds.get(1).select("div[class=td-box cai-num size-32 center pk10-num]").select("span");
            String strNo = spans.get(0).text() + "-" + spans.get(1).text() + "-" + spans.get(2).text() + "-" + spans.get(3).text() + "-" + spans.get(4).text() + "-" + spans.get(5).text() + "-" + spans.get(6).text() + "-" + spans.get(7).text() + "-" + spans.get(8).text() + "-" + spans.get(9).text();
            String gyh1 = tds.get(2).text();
            String gyh2 = tds.get(3).text();
            String gyh3 = tds.get(4).text();
            String lh1 = tds.get(5).text();
            String lh2 = tds.get(6).text();
            String lh3 = tds.get(7).text();
            String lh4 = tds.get(8).text();
            String lh5 = tds.get(9).text();

            PKBean pkBean = new PKBean();
            pkBean.setTime(time);
            pkBean.setStrNo(strNo);
            pkBean.setGyh1(gyh1);
            pkBean.setGyh2(gyh2);
            pkBean.setGyh3(gyh3);
            pkBean.setLh1(lh1);
            pkBean.setLh2(lh2);
            pkBean.setLh3(lh3);
            pkBean.setLh4(lh4);
            pkBean.setLh5(lh5);
            data.add(pkBean);
        }
        //返回数据
        return data;
    }

    @Override
    public void onErrorResponse(VolleyError volleyError, int tag) {
        try {
            lists.clear();
            lists.addAll(getData(CommonData.PK10_ALL));
            adapter.notifyDataSetChanged();
        } catch (Exception ex) {

        }
    }

    @Override
    public void onFailedResponse(String s) {
        try {
            lists.clear();
            lists.addAll(getData(CommonData.PK10_ALL));
            adapter.notifyDataSetChanged();
        } catch (Exception ex) {

        }
    }

    @Override
    protected void onDestroy() {
        if (stringRequest != null) {
            VolleyTool.getInstance(this).release(PKHistoryActivity.class.getSimpleName());
        }
        super.onDestroy();
    }
}
