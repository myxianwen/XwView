# XwView
This is a view of xianwen... 轻松接入鲜闻内容
<br>
<br>
先上图（详情可下载安装demo.apk体验）：
>![image](https://raw.githubusercontent.com/frendyxzc/XwView/master/screenshot/device-2016-11-11-110118.png)
>![image](https://raw.githubusercontent.com/frendyxzc/XwView/master/screenshot/device-2016-11-11-110150.png)
<br>
<br>

**功能**：

>轻松接入鲜闻内容

**原理**：

>封装鲜闻内容

**用法**：

>1.申请鲜闻appid和appkey，[点这里马上申请](http://www.myxianwen.com/ "鲜闻开放平台")；

>2.依赖module - xwview，在项目根目录的build.gradle里添加flatDir，并在app目录下的build.gradle添加dependencies依赖：
```java
allprojects {
    repositories {
        jcenter()
        flatDir {
            dirs '../xwview/libs'
        }
    }
}
```
```java
dependencies {
	compile project(':xwview')
}
```

>3.鲜闻初始化，并设置鲜闻appid和appkey，这里提供两种方法，具体如下：
```java
//方法一，Application继承XwBaseApplication，可参考Demo里的BaseApplication.java
public class BaseApplication extends XwBaseApplication {
	@Override
	public void onCreate() {
		super.onCreate();
        //设置鲜闻appid和appkey，设置后才能接入推荐系统和频道管理
        xwRegisterApp("idxxxxx", "keyxxxxx", new UICallbackListener() {
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
		/** 以下设置可选 */
		//设置是否默认第一个频道为推荐频道，默认为true
		//SettingHelper.setRecommendChannel(false);
        //设置列表背景色
        //SettingHelper.setBackgroundColor(0, "#FFFFFF");
        //设置频道栏颜色
        //SettingHelper.setIndicatorColor(0, "#3F51B5");
        //设置列表新闻标签颜色
        //SettingHelper.setLabelColor(0, "#3F51B5");
        //设置列表下拉刷新控件颜色
        //SettingHelper.setRefreshProgressBarColorId(0, R.color.colorPrimary);
        //设置列表新闻标题和新闻来源/评论数等的字体颜色
        //SettingHelper.setTextColor(0, "#1b1b1b", "#7a7a7a");
        //设置列表加载动画
        //SettingHelper.setProgressbarDrawable(R.drawable.progressbar_rotate_test);
	}
}
```
```java
//方法二，如果您已经有自己的Application，而且不想重新继承，可在onCreate里直接初始化，可参考Demo里的BaseApplication2.java
public class BaseApplication2 extends Application {
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
		/** 设置可选，同方法一 */
    }
}
```

>4.AndroidManifest.xml里配置网络权限，并在application指定您的应用类名：
```java
<!-- 网络权限 -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<!-- 视频亮度控制 -->
<uses-permission android:name="android.permission.WRITE_SETTINGS" />
<!-- APP -->
<application
  android:name=".BaseApplication"
     ...
</application>
```

>5.Activity继承XwNewsListActivity，并调用initXwView()，parent目前只支持FrameLayout（当然您也可以接入到项目已有的view pager里，请参考Demo里的MainActivity2.java和MainActivity3.java）：
```java
public class MainActivity extends XwNewsListActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		FrameLayout mParent = (FrameLayout) findViewById(R.id.activity_main);
		//初始化新闻列表页
		initXwView(mParent);
	}
}
```

>6.在Activity里重写OnNewsItemClickedListener，处理新闻item点击事件（其中News类说明请见附注1）：
```java
@Override
public void OnNewsItemClickedListener(News item, int newsType, int from) {
	super.OnNewsItemClickedListener(item, newsType, from);
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
		case News.TYPE_SUBJECT:
			Log.d(TAG, "专题（专题列表）");
			SubjectListActivity.intentTo(mContext, SubjectListActivity.class, item);
			break;
		case News.TYPE_AD:
			Log.d(TAG, "广告活动");
			AdDetailActivity.intentTo(mContext, AdDetailActivity.class, item);
			break;
		case News.TYPE_SUBJECT_DETAIL:
			Log.d(TAG, "专题（带专题信息的新闻）");
			Intent mainIntent = SubjectListActivity.newIntent(mContext, SubjectListActivity.class, item.topic_id);
			Intent detailIntent = NewsDetailActivity.newIntent(mContext, NewsDetailActivity.class, item, from);
			Intent[] intents = {mainIntent, detailIntent};
			startActivities(intents);
			break;
		default:
			break;
	}
}
```

>7.【可选】接入鲜闻新闻详情页（包含评论功能）
```java
public class NewsDetailActivity extends XwNewsDetailActivity implements IOnNewsItemClickedListener{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        FrameLayout mParent = (FrameLayout) findViewById(R.id.activity_main);
        //指定评论类，当直接接入鲜闻的新闻详情页时，必须同时接入评论页
        setCommendActivityClass(CommendActivity.class);
        //初始化新闻详情页
        initXwView(mParent);
    }
	//重写OnNewsItemClickedListener获取相关阅读点击事件
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
	//重写OnActionItemClickedListener获取分享事件
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
```
```java
public class CommentActivity extends XwCommentDialogActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //去掉TITLE BAR
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
		...
        FrameLayout mParent = (FrameLayout) findViewById(R.id.activity_main);
        //初始化评论页
        initXwView(mParent);
    }
}
```

>8.【可选】接入鲜闻视频详情页（包含评论功能）
```java
public class VideoDetailActivity extends XwVideoDetailActivity implements IOnNewsItemClickedListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        FrameLayout mParent = (FrameLayout) findViewById(R.id.activity_main);
        //指定评论类，当直接接入鲜闻的视频详情页时，必须同时接入评论页（评论页可与新闻页共用）
        setCommendActivityClass(CommendActivity.class);
        //初始化视频详情页
        initXwView(mParent);
    }
	//重写OnNewsItemClickedListener获取相关阅读点击事件
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
	//重写OnActionItemClickedListener获取分享事件
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
```

>9.【可选】接入鲜闻专题列表页
```java
public class SubjectListActivity extends XwSubjectListActivity implements IOnNewsItemClickedListener {
    private static final String TAG = "SubjectListActivity";
    private Context mContext = SubjectListActivity.this;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        FrameLayout mParent = (FrameLayout) findViewById(R.id.activity_main);
        //初始化主题列表页
        initXwView(mParent);
    }
	//重写OnNewsItemClickedListener获取新闻点击事件
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
}
```

>10.【可选】接入鲜闻图片新闻详情页（包含评论列表和评论功能）
```java
public class PicDetailActivity extends XwPicDetailActivity implements IOnNewsItemClickedListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        FrameLayout mParent = (FrameLayout) findViewById(R.id.activity_main);
        //指定评论列表类和评论类，当直接接入鲜闻的图片新闻详情页时，必须同时接入评论列表页和评论页
        setCommendActivityClass(CommentActivity.class, CommentListActivity.class);
        //初始化新闻详情页
        initXwView(mParent);
    }
	//重写OnActionItemClickedListener获取分享事件
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
```
```java
public class CommentListActivity extends XwPicCommentListActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FrameLayout mParent = (FrameLayout) findViewById(R.id.activity_main);
        //指定评论类，当直接接入鲜闻的评论列表页时，必须同时接入评论页
        setCommendActivityClass(CommentActivity.class);
        //初始化评论页
        initXwView(mParent);
    }
}
```

>11.【可选】接入鲜闻广告活动详情页
```java
public class AdDetailActivity extends XwAdDetailActivity implements IOnNewsItemClickedListener {
    private static final String TAG = "AdDetailActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FrameLayout mParent = (FrameLayout) findViewById(R.id.activity_main);
        //初始化广告(活动)详情页
        initXwView(mParent);
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
```

>12.【可选】若有打开混淆，请在app的proguard-rules.pro里添加以下keep：
```
# gson
-keepattributes Signature
-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *; }
# sdk
-dontwarn com.iimedia.xwsdk.**
-keep public class com.iimedia.xwsdk.**{*;}
# facebook
-dontwarn com.facebook.**
-keep class com.facebook.**{*;}
# google
-dontwarn com.google.**
-keep class com.google.**{*;}
# volley
-dontwarn com.android.volley.**
-keep class com.android.volley.**{*;}
# image loader
-dontwarn com.nostra13.**
-keep public class com.nostra13.**{*;}
# 保持哪些类不被混淆
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * implements java.io.Serializable {*;}
# 保持 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}
# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
# 保持自定义控件类不被混淆
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
# 保持自定义控件类不被混淆
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}
# 保持枚举 enum 类不被混淆
-keepclassmembers enum * {
 public static **[] values();
 public static ** valueOf(java.lang.String);
}
# 保持 Parcelable 不被混淆
-keep class * implements android.os.Parcelable {
 public static final android.os.Parcelable$Creator *;
}
```

<br>
<br>

**附注**：

>1.新闻News类说明：
>![image](https://raw.githubusercontent.com/frendyxzc/XwView/master/screenshot/20161205115129.png)<br>

>2.常见问题

>>a)编译时报lite-orm包重复 <br>
>>xwview中已打包lite-orm-1.9.1.jar，可直接删除您工程中相应jar包，依赖于xwview module即可
>>

>>b)是否能接入到项目已有的View Pager里？<br>
>>可以，请参考Demo里的MainActivity2.java，效果图如下：<br>
>>![image](https://raw.githubusercontent.com/frendyxzc/XwView/master/screenshot/device-2016-12-08-125952.png)
>>

>>c)有没有Eclpise的版本？<br>
>>有，[Eclpise版本请点击这里](https://github.com/myxianwen/XwView_Eclpise)
>>

>>d)能否只接入某个特定的频道页面？<br>
>>1.1.8版本开始支持，请参考Demo里的MainActivity3.java，可以拿到已订阅的某个特定频道的Fragment
>>

<br>
<br>
<br>

**Releasenote**:
>1.0.0  开放鲜闻新闻列表 <br>
>1.0.1  开放新闻item点击跳转接口；增加鲜闻appid设置接口 <br>
>1.0.2  开放鲜闻新闻详情页（包含评论功能）<br>
>1.0.3  重写推荐系统注册接口；增加频道后台获取功能 <br>
>1.0.4  开放鲜闻视频详情页（包含评论功能）<br>
>1.0.5  开放鲜闻专题列表页 <br>
>1.0.6  混淆和资源优化，Size减半 <br>
>1.0.7  开放图片新闻详情页 :point_left: <br>
>1.0.8  开放评论列表页（用于图片新闻）；开放部分样式设置接口 <br>
>1.0.9  调优视频页样式和交互；开放列表背景颜色设置接口；添加签名和混淆；添加用户信息同步处理 <br>
>1.0.10 开放广告活动页 <br>
>1.1.0  调优体育赛事信息样式和详情页样式；开放列表新闻Title和Description颜色设置接口；更新签名 :point_left: <br>
>1.1.1  开放列表加载动画设置接口；添加Application免继承继承方法 <br>
>1.1.2  替换加载异常图片；修改频道拉取问题；精简资源减少包大小 <br>
>1.1.3  修改接入时遇到的一些冲突问题；更新说明文档 <br>
>1.1.4  调整视频详情页样式；修改已知bug <br>
>1.1.5  调整视频音量/亮度控制交互和样式；替换评论和新闻详情页接口；精简资源；修改已知bug :point_left: <br>
>1.1.6  精简资源；开放推荐频道设置接口 <br>
>1.1.7  新闻列表接口添加参数；同步部分Eclpise适配修改 <br>
>1.1.8  开放特定页面接入方法 :point_left: <br>
<br>
<br>

**iOS版本**
>please visit: https://github.com/full-of-fire/XWNewsFramework
<br>
<br>

Contact Us: 3176385478@qq.com
