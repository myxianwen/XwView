package cn.myxianwen.view;

import android.util.Log;

import com.iimedia.xwsdk.XwBaseApplication;
import com.iimedia.xwsdk.net.listener.UICallbackListener;
import com.iimedia.xwsdk.setting.SettingHelper;
import com.iimedia.xwsdk.setting.SettingUtils;

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

        //设置用户昵称和头像，这里默认设置为“某某应用的用户”，可在登录后设置相应的昵称和头像
        SettingHelper.setUserInfo(this, SettingUtils.getApplicationName(this) + "的用户",
                "http://images.iimedia.cn/10001aa87a43d23ea19a3a04ea9e2c301724d24a29690911e1ef304bf72a1d577e72a");
        //设置列表背景色
        //SettingHelper.setBackgroundColor(0, "#FFFFFF");
        //设置频道栏颜色
        //SettingHelper.setIndicatorColor(0, "#3F51B5");
        //设置列表新闻标签颜色
        //SettingHelper.setLabelColor(0, "#3F51B5");
        //设置列表下拉刷新控件颜色
        //SettingHelper.setRefreshProgressBarColorId(0, R.color.colorPrimary);
    }
}
