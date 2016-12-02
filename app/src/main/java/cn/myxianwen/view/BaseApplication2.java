package cn.myxianwen.view;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.iimedia.xwsdk.net.listener.UICallbackListener;
import com.iimedia.xwsdk.setting.SettingHelper;
import com.iimedia.xwsdk.setting.SettingUtils;

/**
 * Created by iiMedia on 2016/11/11.
 */

public class BaseApplication2 extends Application {
    private String TAG = "BaseApplication";
    private Context mContext = BaseApplication2.this;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化鲜闻
        SettingHelper.xwInit(BaseApplication2.this);
        //设置鲜闻appid和appkey，设置后才能接入推荐系统和频道管理，后续会开放申请平台
        SettingHelper.xwRegisterApp(mContext, "idxxxxx", "keyxxxxx", new UICallbackListener() {
            @Override
            public void onSuccess(Object data) {
                String userid = (String) data;
                //设置用户昵称和头像，这里默认设置为“某某应用的用户”，可在登录后设置相应的昵称和头像
                SettingHelper.setUserInfo(mContext, SettingUtils.getApplicationName(mContext) + "的用户",
                        "http://images.iimedia.cn/10001aa87a43d23ea19a3a04ea9e2c301724d24a29690911e1ef304bf72a1d577e72a");
            }
            @Override
            public void onFailure(int errorEvent, String message) {
                Log.e(TAG, "xw register failed: errorEvent=" + errorEvent + ", message=" + message);
            }
        });

        //设置列表背景色
        //SettingHelper.setBackgroundColor(0, "#FFFFFF");
        //设置频道栏颜色
        //SettingHelper.setIndicatorColor(0, "#3F51B5");
        //设置列表新闻标签颜色
        //SettingHelper.setLabelColor(0, "#3F51B5");
        //设置列表下拉刷新控件颜色
        //SettingHelper.setRefreshProgressBarColorId(0, R.color.colorPrimary);
        //设置列表新闻Title和Subtext颜色
        //SettingHelper.setTextColor(0, "#1b1b1b", "#7a7a7a");
        //设置列表加载动画
        //SettingHelper.setProgressbarDrawable(R.drawable.progressbar_rotate_test);
    }
}
