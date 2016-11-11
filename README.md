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
		//设置鲜闻appid，设置后才能接入推荐系统，后续会开放申请平台
		setXwAppid("xxxxx");
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

>4.Activity继承XwBaseActivity，并调用initNewsListView()，parent目前只支持FrameLayout：
```java
public class MainActivity extends XwBaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		FrameLayout mParent = (FrameLayout) findViewById(R.id.activity_main);
		initNewsListView(mParent);
	}
}
```

>5.在Activity里重写OnNewsItemClickedListener，处理新闻item点击事件：
```java
@Override
public void OnNewsItemClickedListener(News item, int newsType) {
	super.OnNewsItemClickedListener(item, newsType);
	switch (newsType) {
		case News.TYPE_NEWS:
			Log.d(TAG, "普通新闻");
			break;
		case News.TYPE_VIDEO:
			Log.d(TAG, "视频");
			break;
		case News.TYPE_PIC:
			Log.d(TAG, "图片新闻");
			break;
		case News.TYPE_SUBJECT:
			Log.d(TAG, "专题（专题列表）");
			break;
		case News.TYPE_AD:
			Log.d(TAG, "广告活动");
			break;
		case News.TYPE_SUBJECT_DETAIL:
			Log.d(TAG, "专题（带专题信息的新闻）");
			break;
		default:
			break;
	}
}
```

<br>
<br>
**Todo List**：
>1.频道管理接入 <br>
>2.搜索功能接入 <br>
>3.推荐系统接入 <br>
>4.... <br>
<br>
<br>

**Releasenote**:
>1.0.0 实现鲜闻列表 <br>
>1.0.1 开放新闻item点击跳转接口；增加鲜闻appid设置接口 <br>
<br>
<br>

Contact Us: 3176385478@qq.com
