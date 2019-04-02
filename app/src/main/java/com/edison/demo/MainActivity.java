package com.edison.demo;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.tabs.TabLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.ll_top)
    LinearLayout llTop;
    @BindView(R.id.evaluate_recyclerview)
    RecyclerView evaluateRecyclerview;
    @BindView(R.id.foot_rcv)
    RecyclerView footRec;
    @BindView(R.id.ll_mid)
    LinearLayout llMid;
    @BindView(R.id.ll_foot)
    LinearLayout llFoot;
    @BindView(R.id.scrollview)
    CustomScrollView scrollview;


    private String[] titles = {"商品", "评论", "详情"};

    private String[] bannner = {
            "http://47.104.76.188:8088/eapple/userfiles/63fe4892f6674a9c8d23ee86a8a43ceb/files/mobi/productInfo/2018/12/ere1.png",
            "http://47.104.76.188:8088/eapple/userfiles/63fe4892f6674a9c8d23ee86a8a43ceb/files/mobi/productInfo/2018/12/010304001-5(2).jpg"
    };

    private String[] recData = {
            "http://47.104.76.188:8088/eapple/userfiles/2ab8c57a915444c2ac5d7de3a12d7bc6/files/mobi/productInfo/2018/03/SDHG_01.jpg",
            "http://47.104.76.188:8088/eapple/userfiles/2ab8c57a915444c2ac5d7de3a12d7bc6/files/mobi/productInfo/2018/03/SDHG_02.jpg",
            "http://47.104.76.188:8088/eapple/userfiles/2ab8c57a915444c2ac5d7de3a12d7bc6/files/mobi/productInfo/2018/03/SDHG_03.jpg",
            "http://47.104.76.188:8088/eapple/userfiles/2ab8c57a915444c2ac5d7de3a12d7bc6/files/mobi/productInfo/2018/03/SDHG_04.jpg",
            "http://47.104.76.188:8088/eapple/userfiles/2ab8c57a915444c2ac5d7de3a12d7bc6/files/mobi/productInfo/2018/03/SDHG_05.jpg",
            "http://47.104.76.188:8088/eapple/userfiles/2ab8c57a915444c2ac5d7de3a12d7bc6/files/mobi/productInfo/2018/03/SDHG_06.jpg",
            "http://47.104.76.188:8088/eapple/userfiles/2ab8c57a915444c2ac5d7de3a12d7bc6/files/mobi/productInfo/2018/03/SDHG_07.jpg",
            "http://47.104.76.188:8088/eapple/userfiles/2ab8c57a915444c2ac5d7de3a12d7bc6/files/mobi/productInfo/2018/03/SDHG_08.jpg",
            "http://47.104.76.188:8088/eapple/userfiles/2ab8c57a915444c2ac5d7de3a12d7bc6/files/mobi/productInfo/2018/03/SDHG_09.jpg",
            "http://47.104.76.188:8088/eapple/userfiles/2ab8c57a915444c2ac5d7de3a12d7bc6/files/mobi/productInfo/2018/03/SDHG_10.jpg",
            "http://47.104.76.188:8088/eapple/userfiles/2ab8c57a915444c2ac5d7de3a12d7bc6/files/mobi/productInfo/2018/03/SDHG_11.jpg",
            "http://47.104.76.188:8088/eapple/userfiles/2ab8c57a915444c2ac5d7de3a12d7bc6/files/mobi/productInfo/2018/03/SDHG_12.jpg",
            "http://47.104.76.188:8088/eapple/userfiles/2ab8c57a915444c2ac5d7de3a12d7bc6/files/mobi/productInfo/2018/03/SDHG_13.jpg",
            "http://47.104.76.188:8088/eapple/userfiles/2ab8c57a915444c2ac5d7de3a12d7bc6/files/mobi/productInfo/2018/03/SDHG_14.jpg",
            "http://47.104.76.188:8088/eapple/userfiles/2ab8c57a915444c2ac5d7de3a12d7bc6/files/mobi/productInfo/2018/03/SDHG_15.jpg"
    };


    private List<LinearLayout> anchorList = new ArrayList<>();

    private boolean isFlag;
    //用于判断scrollview的滑动由谁引起的，避免通过点击tabLayout引起的scrollview滑动问题
    private boolean isScroll = false;
    //当scrollview 在同一模块中滑动时，则不再去调用tabLayout.setScrollPosition刷新标签。
    private int lastPos = 0;
    private DetailImgAdapter detailImgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initData();

    }


    private void initView() {

        initTab();

        initSroll();

        initRec();

    }

    private void initRec() {
        //详情
        footRec.setNestedScrollingEnabled(false);
        footRec.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setAutoMeasureEnabled(true);
        footRec.setLayoutManager(layoutManager);
        detailImgAdapter = new DetailImgAdapter();
        footRec.setAdapter(detailImgAdapter);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initSroll() {
        anchorList.add(llTop);
        anchorList.add(llMid);
        anchorList.add(llFoot);

        //这里是用来判断  scrollview自己滑动的时候
        scrollview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //当由scrollview触发时，isScroll 置true
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    isScroll = true;
                }
                return false;
            }
        });


        scrollview.setCallbacks(new CustomScrollView.Callbacks() {
            @Override
            public void onScrollChanged(int x, int y, int oldx, int oldy) {
                if (isScroll) {
                    for (int i = titles.length - 1; i >= 0; i--) {

                        //根据滑动距离，对比各模块距离父布局顶部的高度判断
                        if (y > anchorList.get(i).getTop() - 10) {
                            setScrollPos(i);
                            Log.e("okgo", "y=" + y + "===getTop" + anchorList.get(i).getTop() + "postion" + lastPos);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void initTab() {
        for (int i = 0; i < titles.length; i++) {
            tab.addTab(tab.newTab().setText(titles[i]));
        }
        Objects.requireNonNull(tab.getTabAt(0)).select();

        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //点击标签，使scrollview滑动，isScroll置false
                isScroll = false;
                int pos = tab.getPosition();
                int top = anchorList.get(pos).getTop();
                scrollview.scrollTo(0, top);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initBanner() {

        banner.setImages(Arrays.asList(bannner))
                .setDelayTime(5000)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)//是否显示指示器
                .isAutoPlay(true)//是否自动播放
                .setImageLoader(new GlideImageLoader())//图片加载
                .start();

    }

    //tablayout对应标签的切换
    private void setScrollPos(int newPos) {
        if (lastPos != newPos) {
            //该方法不会触发tablayout 的onTabSelected 监听
            // tab.setScrollPosition(newPos, 0, true);
            tab.setScrollPosition(newPos, 0, true);
        }
        lastPos = newPos;
    }

    private void initData() {

        initBanner();

        detailImgAdapter.setNewData(Arrays.asList(recData));
    }
}
