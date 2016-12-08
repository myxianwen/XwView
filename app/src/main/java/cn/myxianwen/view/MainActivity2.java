package cn.myxianwen.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.iimedia.xwsdk.activity.XwNewsListActivity;
import com.iimedia.xwsdk.model.entity.News;

import java.util.ArrayList;

import devlight.io.library.ntb.NavigationTabBar;

public class MainActivity2 extends XwNewsListActivity {
    private static final String TAG = "MainActivity";
    private Context mContext = MainActivity2.this;

    private View xw_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horizontal_ntb);

        initUI();
    }

    @Override
    public void OnNewsItemClickedListener(News item, int newsType, int from) {
        super.OnNewsItemClickedListener(item, newsType, from);
        switch (newsType) {
            case News.TYPE_NEWS:
                Log.d(TAG, "普通新闻");
                NewsDetailActivity.intentTo(mContext, NewsDetailActivity.class, item, from);
                break;
            case News.TYPE_VIDEO:
                Log.d(TAG, "视频");
                VideoDetailActivity.intentTo(mContext, VideoDetailActivity.class, item, from);
                break;
            case News.TYPE_PIC:
                Log.d(TAG, "图片新闻");
                PicDetailActivity.intentTo(mContext, PicDetailActivity.class, item, from);
                break;
            case News.TYPE_SUBJECT:
                Log.d(TAG, "专题（专题列表）");
                SubjectListActivity.intentTo(mContext, SubjectListActivity.class, item);
                break;
            case News.TYPE_AD:
                Log.d(TAG, "广告活动");
                AdDetailActivity.intentTo(mContext, AdDetailActivity.class, item);
                break;
            case News.TYPE_SUBJECT_DETAIL:
                Log.d(TAG, "专题（带专题信息的新闻）");
                Intent mainIntent = SubjectListActivity.newIntent(mContext, SubjectListActivity.class, item.topic_id);
                Intent detailIntent = NewsDetailActivity.newIntent(mContext, NewsDetailActivity.class, item, from);
                Intent[] intents = {mainIntent, detailIntent};
                startActivities(intents);
                break;
            default:
                break;
        }
    }

    private void initUI() {
        final ViewPager viewPager = (ViewPager) findViewById(R.id.vp_horizontal_ntb);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return 3;
            }

            @Override
            public boolean isViewFromObject(final View view, final Object object) {
                return view.equals(object);
            }

            @Override
            public void destroyItem(final View container, final int position, final Object object) {
                ((ViewPager) container).removeView((View) object);
            }

            @Override
            public Object instantiateItem(final ViewGroup container, final int position) {
                if(position == 1) {
                    final View view  = LayoutInflater.from(
                            getBaseContext()).inflate(R.layout.item_xwview, null, false);
                    FrameLayout mParent = (FrameLayout) view.findViewById(R.id.layout_xwview);
                    initXwView(mParent);

                    container.addView(view);
                    return view;
                } else {
                    final View view = LayoutInflater.from(
                            getBaseContext()).inflate(R.layout.item_vp, null, false);

                    final TextView txtPage = (TextView) view.findViewById(R.id.txt_vp_item_page);
                    txtPage.setText(String.format("Page #%d", position));

                    container.addView(view);
                    return view;
                }
            }
        });

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(android.R.drawable.ic_dialog_alert),
                        Color.parseColor(colors[0]))
                        .selectedIcon(getResources().getDrawable(android.R.drawable.ic_dialog_alert))
                        .title("One")
                        .badgeTitle("hello")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(android.R.drawable.ic_dialog_info),
                        Color.parseColor(colors[1]))
//                        .selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("Two")
                        .badgeTitle("xw")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(android.R.drawable.ic_dialog_dialer),
                        Color.parseColor(colors[2]))
                        .selectedIcon(getResources().getDrawable(android.R.drawable.ic_dialog_dialer))
                        .title("Three")
                        .badgeTitle("view")
                        .build()
        );

        navigationTabBar.setModels(models);
        navigationTabBar.setViewPager(viewPager, 1);
        navigationTabBar.setBgColor(R.color.colorPrimary);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);
    }
}
