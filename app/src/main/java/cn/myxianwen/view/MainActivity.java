package cn.myxianwen.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.iimedia.xwsdk.activity.XwBaseActivity;
import com.iimedia.xwsdk.model.entity.News;

public class MainActivity extends XwBaseActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout mParent = (FrameLayout) findViewById(R.id.activity_main);
        initNewsListView(mParent);
    }

    @Override
    public void OnNewsItemClickedListener(News item, int newsType) {
        super.OnNewsItemClickedListener(item, newsType);
        switch (newsType) {
            case News.TYPE_NEWS:
                Log.d(TAG, "普通新闻");
                break;
            case News.TYPE_VIDEO:
                Log.d(TAG, "视频");
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
