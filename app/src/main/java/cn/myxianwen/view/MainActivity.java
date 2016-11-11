package cn.myxianwen.view;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.iimedia.xwsdk.activity.XwBaseActivity;

public class MainActivity extends XwBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout mParent = (FrameLayout) findViewById(R.id.activity_main);
        initNewsListView(mParent);
    }
}
