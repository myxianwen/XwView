package cn.myxianwen.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.iimedia.xwsdk.activity.XwNewsListActivity;
import com.iimedia.xwsdk.model.entity.News;

public class MainActivity extends XwNewsListActivity {
    private static final String TAG = "MainActivity";
    private Context mContext = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout mParent = (FrameLayout) findViewById(R.id.activity_main);
        initXwView(mParent);

        FloatingActionButton mBtn = (FloatingActionButton) findViewById(R.id.button_goto_viewpager);
        mBtn.setVisibility(View.VISIBLE);
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, MainActivity2.class));
            }
        });
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
}
