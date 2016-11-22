package cn.myxianwen.view;

import android.util.Log;

import com.iimedia.xwsdk.XwBaseApplication;
import com.iimedia.xwsdk.net.listener.UICallbackListener;
import com.iimedia.xwsdk.setting.SettingHelper;

/**
 * Created by iiMedia on 2016/11/11.
 */

public class BaseApplication extends XwBaseApplication {
    private String TAG = "BaseApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        //设置鲜闻appid和appkey，设置后才能接入推荐系统和频道管理，后续会开放申请平台
        xwRegisterApp("idxxxxx", "keyxxxxx", new UICallbackListener() {
            @Override
            public void onSuccess(Object data) {
                String userid = (String) data;
            }
            @Override
            public void onFailure(int errorEvent, String message) {
                Log.e(TAG, "xw register failed: errorEvent=" + errorEvent + ", message=" + message);
            }
        });

        //设置用户昵称，可在初始化时设置默认用户名或在登录后设置
        SettingHelper.setUserNickname("xw");
        //设置用户头像
        SettingHelper.setUserHeadIcon("http://images.iimedia.cn/10001e369562bd36cf41efaaa545ba308226e8bd7588b2eb4938008138fc4c22d15d0");
        //设置频道栏颜色
        //SettingHelper.setIndicatorColor(0, "#3F51B5");
        //设置列表新闻标签颜色
        //SettingHelper.setLabelColor(0, "#3F51B5");
        //设置列表下拉刷新控件颜色
        //SettingHelper.setRefreshProgressBarColorId(0, R.color.colorPrimary);
    }
}
