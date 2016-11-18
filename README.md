# XwView
This is a view of xianwen... 轻松接入鲜闻内容
<br>
<br>
先上图：
>![image](https://raw.githubusercontent.com/frendyxzc/XwView/master/screenshot/device-2016-11-11-110118.png)
>![image](https://raw.githubusercontent.com/frendyxzc/XwView/master/screenshot/device-2016-11-11-110150.png)
<br>
<br>

**功能**：

>轻松接入鲜闻内容

**原理**：

>封装鲜闻内容

**用法**：

>1.依赖module - xwview，并在项目顶层的build.gradle里添加flatDir;
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

>2.Application继承XwBaseApplication，并设置鲜闻appid，如下：
```java
public class BaseApplication extends XwBaseApplication {
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
```

>3.AndroidManifest.xml里配置网络权限，并在application添加name：
```java
<!-- 网络权限 -->
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
<!-- APP -->
<application
  android:name=".BaseApplication"
     ...
</application>
```

>4.Activity继承XwNewsListActivity，并调用initXwView()，parent目前只支持FrameLayout：
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

>5.在Activity里重写OnNewsItemClickedListener，处理新闻item点击事件：
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

>6.【可选】接入鲜闻新闻详情页（包含评论功能）
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
        FrameLayout mParent = (FrameLayout) findViewById(R.id.activity_main);
        //初始化评论页
        initXwView(mParent);
    }
}
```

>7.【可选】接入鲜闻视频详情页（包含评论功能）
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

>8.【可选】接入鲜闻专题列表页
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

>9.【可选】接入鲜闻图片新闻详情页
```java
public class PicDetailActivity extends XwPicDetailActivity implements IOnNewsItemClickedListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        FrameLayout mParent = (FrameLayout) findViewById(R.id.activity_main);
        //指定评论类，当直接接入鲜闻的图片新闻详情页时，必须同时接入评论页（评论页可与新闻页共用）
        setCommendActivityClass(CommendActivity.class);
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

<br>
<br>
**Todo List**：
>1.开放推荐系统/频道管理接入平台 <br>
>2.视频页样式和交互调优 <br>
>3.开放图片新闻评论页 <br>
>4.... <br>
<br>
<br>

**Releasenote**:
>1.0.0 开放鲜闻新闻列表 <br>
>1.0.1 开放新闻item点击跳转接口；增加鲜闻appid设置接口 <br>
>1.0.2 开放鲜闻新闻详情页（包含评论功能）<br>
>1.0.3 重写推荐系统注册接口；增加频道后台获取功能 <br>
>1.0.4 开放鲜闻视频详情页（包含评论功能）<br>
>1.0.5 开放鲜闻专题列表页 <br>
>1.0.6 混淆和资源优化，Size减半 <br>
>1.0.7 开放图片新闻详情页 <br>
<br>
<br>

Contact Us: 3176385478@qq.com
