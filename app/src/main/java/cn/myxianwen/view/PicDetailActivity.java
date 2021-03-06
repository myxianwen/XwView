package cn.myxianwen.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.FrameLayout;

import com.iimedia.xwsdk.activity.Common;
import com.iimedia.xwsdk.activity.XwPicDetailActivity;
import com.iimedia.xwsdk.interfaces.IOnNewsItemClickedListener;
import com.iimedia.xwsdk.model.entity.News;

/**
 * Created by iiMedia on 2016/11/11.
 */

public class PicDetailActivity extends XwPicDetailActivity implements IOnNewsItemClickedListener {
    private static final String TAG = "PicDetailActivity";
    private Context mContext = PicDetailActivity.this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout mParent = (FrameLayout) findViewById(R.id.activity_main);
        //指定评论列表类和评论类，当直接接入鲜闻的图片新闻详情页时，必须同时接入评论列表页和评论页
        setCommendActivityClass(CommentActivity.class, CommentListActivity.class);
        //初始化新闻详情页
        initXwView(mParent);
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
