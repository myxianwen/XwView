package cn.myxianwen.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.FrameLayout;

import com.iimedia.xwsdk.activity.Common;
import com.iimedia.xwsdk.activity.XwNewsDetailActivity;
import com.iimedia.xwsdk.interfaces.IOnNewsItemClickedListener;
import com.iimedia.xwsdk.model.entity.News;

/**
 * Created by iiMedia on 2016/11/11.
 */

public class NewsDetailActivity extends XwNewsDetailActivity implements IOnNewsItemClickedListener {
    private static final String TAG = "NewsDetailActivity";
    private Context mContext = NewsDetailActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout mParent = (FrameLayout) findViewById(R.id.activity_main);
        //指定评论类，当直接接入鲜闻的新闻详情页时，必须同时接入评论页
        setCommendActivityClass(CommendActivity.class);
        //初始化新闻详情页
        initNewsDetailView(mParent);
    }

    @Override
    public void OnNewsItemClickedListener(News item, int newsType, int from) {
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
            default:
                break;
        }
    }

    @Override
    public void OnActionItemClickedListener(News item, int actionType) {
        switch (actionType) {
            case Common.XWNEWS_ACTION_SHARE:
                Log.d(TAG, "分享");
                break;
            default:
                break;
        }
    }
}
