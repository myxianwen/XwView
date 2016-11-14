package cn.myxianwen.view;

import com.iimedia.xwsdk.XwBaseApplication;

/**
 * Created by iiMedia on 2016/11/11.
 */

public class BaseApplication extends XwBaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        //设置鲜闻appid和appkey，设置后才能接入推荐系统，后续会开放申请平台
        xwRegisterApp("idxxxxx", "keyxxxxx");
    }
}
