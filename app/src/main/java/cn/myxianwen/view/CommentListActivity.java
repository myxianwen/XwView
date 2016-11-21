package cn.myxianwen.view;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.iimedia.xwsdk.activity.XwPicCommentListActivity;

/**
 * Created by iiMedia on 2016/11/14.
 */

public class CommentListActivity extends XwPicCommentListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout mParent = (FrameLayout) findViewById(R.id.activity_main);
        //指定评论类，当直接接入鲜闻的评论列表页时，必须同时接入评论页
        setCommendActivityClass(CommentActivity.class);
        //初始化评论页
        initXwView(mParent);
    }
}
