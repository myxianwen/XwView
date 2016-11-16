package cn.myxianwen.view;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
        initNewsListView(mParent);
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
                break;
            case News.TYPE_SUBJECT:
                Log.d(TAG, "专题（专题列表）");
                break;
            case News.TYPE_AD:
                Log.d(TAG, "广告活动");
                break;
            case News.TYPE_SUBJECT_DETAIL:
                Log.d(TAG, "专题（带专题信息的新闻）");
                break;
            default:
                break;
        }
    }
}
