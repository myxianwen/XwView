package cn.myxianwen.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.iimedia.xwsdk.activity.Common;
import com.iimedia.xwsdk.interfaces.IOnNewsItemClickedListener;
import com.iimedia.xwsdk.model.entity.News;

/**
 * Created by iiMedia on 2017/2/5.
 */

public class MainActivity3 extends FragmentActivity implements IOnNewsItemClickedListener {
    private static final String TAG = "MainActivity3";
    private Context mContext = MainActivity3.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main,
                Common.getChannelFragment(mContext, "段子")).commitAllowingStateLoss();
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

    @Override
    public void OnActionItemClickedListener(News news, int actionType) {
        switch (actionType) {
            case Common.XWNEWS_ACTION_SHARE:
                Log.d(TAG, "分享");
                break;
            default:
                break;
        }
    }
}
