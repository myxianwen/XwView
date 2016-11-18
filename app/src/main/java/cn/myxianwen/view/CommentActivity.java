package cn.myxianwen.view;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.iimedia.xwsdk.activity.XwCommentDialogActivity;


/**
 * Created by iiMedia on 2016/11/14.
 */

public class CommentActivity extends XwCommentDialogActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout mParent = (FrameLayout) findViewById(R.id.activity_main);
        //初始化评论页
        initXwView(mParent);
    }
}
