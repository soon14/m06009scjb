package com.cg.mrice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.cg.mrice.adapter.PKBeanAdapter;
import com.cg.mrice.data.CommonData;
import com.cg.mrice.fragment.*;
import com.cg.mrice.http.VolleyCallBack;
import com.cg.mrice.http.VolleyTool;
import com.cg.mrice.http.VolleyUtils;
import com.cg.mrice.model.LotteryBean;
import com.cg.mrice.model.PKBean;
import com.cg.mrice.model.SportData;
import com.cg.mrice.ui.PKHistoryActivity;
import com.cg.mrice.ui.SHPlanActivity;
import com.cg.mrice.ui.ZHPlanActivity;
import com.cg.mrice.ui.ZSPKActivity;
import com.cg.mrice.utils.ToastUtil;
import com.cg.mrice.utils.Utils;
import com.cg.mrice.view.CircleMenuLayout;
import com.cg.mrice.view.ClipView;
import com.cg.mrice.view.FlowTransformer;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr on 2018/4/14.
 */

public class PanActivity extends AppCompatActivity implements VolleyCallBack, View.OnClickListener {

    private String pk_url = "https://www.pk106.com/draw-pk10-today.html";
    private long lastClickTime = 0;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    public static List<LotteryBean.LotteryDetails> datas = new ArrayList<>();
    public static List<PKBean> lists = new ArrayList<>();
    private StringRequest request = null;
    private StringRequest stringRequest = null;

    @BindView(R.id.rv)
    RecyclerView recyclerView;
    private PKBeanAdapter adapter;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    private ProgressDialog progressDialog;
    LinearLayout lskj;
    LinearLayout hmyc;
    LinearLayout zjsc;
    LinearLayout zsfx;
    private String[] mItemTexts = new String[]{
            "历史开奖", "追号计划", "专家杀号", "露珠分析", "个人中心"};
    private int[] mItemImgs = new int[]{R.drawable.lottery_icon_single,
            R.drawable.lottery_icon_ssq, R.drawable.lottery_icon_pl3,
            R.drawable.lottery_icon_pl5, R.drawable.lottery_icon_qxc};
    private CircleMenuLayout mCircleMenuLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pan);
        ButterKnife.bind(this);
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);

        mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {

                switch (pos) {
                    case 0:
                        startActivity(new Intent(PanActivity.this, PKHistoryActivity.class));
                        break;
                    case 1:
                        startActivity(new Intent(PanActivity.this, ZHPlanActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(PanActivity.this, SHPlanActivity.class));
                        break;
                    case 3:
                        startActivity(new Intent(PanActivity.this, ZSPKActivity.class));
                        break;
                    case 4:
                        startActivity(new Intent(PanActivity.this, UserActivity.class));
                        break;
                }
            }

            @Override
            public void itemCenterClick(View view) {

            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("正在加载,请稍后...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);

        View v = LayoutInflater.from(this).inflate(R.layout.layout_pan_head, null);
        lskj = (LinearLayout) v.findViewById(R.id.lskj);
        hmyc = (LinearLayout) v.findViewById(R.id.hmyc);
        zjsc = (LinearLayout) v.findViewById(R.id.zjsc);
        zsfx = (LinearLayout) v.findViewById(R.id.zsfx);
        lskj.setOnClickListener(this);
        hmyc.setOnClickListener(this);
        zjsc.setOnClickListener(this);
        zsfx.setOnClickListener(this);
        adapter = new PKBeanAdapter(R.layout.layout_item_pk10, lists);
        adapter.addHeaderView(v);
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
        initNet();
    }

    private void initPK10() {
        stringRequest = new VolleyUtils(2000, this).getStringRequest(pk_url);
        if (stringRequest != null) {
            stringRequest.setTag(PanActivity.class.getSimpleName());
            VolleyTool.getInstance(this).getmRequestQueue().add(stringRequest);
        }
    }

    private void initNet() {
        HashMap params = new HashMap<String, String>();
        params.put("mobileType", "android");
        request = new VolleyUtils(1000, this).getStringRequest(params, Utils.GET_LOTTERY_DATA);
        if (request != null) {
            request.setTag(PanActivity.class.getSimpleName());
            VolleyTool.getInstance(this).getmRequestQueue().add(request);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lskj:
                startActivity(new Intent(PanActivity.this, PKHistoryActivity.class));
                break;
            case R.id.hmyc:
                startActivity(new Intent(PanActivity.this, ZHPlanActivity.class));
                break;
            case R.id.zjsc:
                startActivity(new Intent(PanActivity.this, SHPlanActivity.class));
                break;
            case R.id.zsfx:
                startActivity(new Intent(PanActivity.this, ZSPKActivity.class));
                break;
        }
    }

    @Override
    public void onResponse(String s, int tag) {
        if (isFinishing()) {
            return;
        }
        switch (tag) {
            case 1000:
                LotteryBean lotteryBean;
                try {
                    lotteryBean = CommonData.getGson().fromJson(s, LotteryBean.class);
                    if (lotteryBean.getData() != null && lotteryBean.getData().size() > 0) {
                        datas.addAll(lotteryBean.getData());
                    } else {
                        lotteryBean = CommonData.getGson().fromJson(CommonData.DATA_ALL, LotteryBean.class);
                        datas.addAll(lotteryBean.getData());
                    }
                } catch (Exception ex) {
                    lotteryBean = CommonData.getGson().fromJson(CommonData.DATA_ALL, LotteryBean.class);
                    datas.addAll(lotteryBean.getData());
                }
                break;
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

    @Override
    public void onErrorResponse(VolleyError volleyError, int tag) {
        if (tag == 1000) {
            LotteryBean lotteryBean = CommonData.getGson().fromJson(CommonData.DATA_ALL, LotteryBean.class);
            datas.addAll(lotteryBean.getData());
        }
        if (tag == 2000) {
            try {
                lists.addAll(getData(CommonData.PK10_ALL));
                adapter.notifyDataSetChanged();
            } catch (Exception ex) {

            }
        }
    }

    @Override
    public void onFailedResponse(String s) {
        LotteryBean lotteryBean = CommonData.getGson().fromJson(CommonData.DATA_ALL, LotteryBean.class);
        datas.clear();
        datas.addAll(lotteryBean.getData());

        try {
            lists.clear();
            lists.addAll(getData(CommonData.PK10_ALL));
            adapter.notifyDataSetChanged();
        } catch (Exception ex) {

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
    public void onBackPressed() {
        if (lastClickTime == 0) {
            lastClickTime = System.currentTimeMillis();
            ToastUtil.showToast(getString(R.string.login_out));
            return;
        }

        final long interval = System.currentTimeMillis() - lastClickTime;

        lastClickTime = System.currentTimeMillis();

        if (interval > 2000) {
            ToastUtil.showToast(getString(R.string.login_out));
        } else {
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onDestroy() {
        if (request != null) {
            VolleyTool.getInstance(this).release(PanActivity.class.getSimpleName());
        }
        if (stringRequest != null) {
            VolleyTool.getInstance(this).release(PanActivity.class.getSimpleName());
        }
        super.onDestroy();
    }
}
