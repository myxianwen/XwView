package cn.myxianwen.view;

import android.util.Log;

import com.iimedia.xwsdk.XwBaseApplication;
import com.iimedia.xwsdk.net.listener.UICallbackListener;

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
    }
}
