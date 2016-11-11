# XwView
This is a view of xianwen... 轻松接入鲜闻内容
<br>
<br>
先上图：
>![image](https://raw.githubusercontent.com/frendyxzc/XwsdkPlugin/master/screenshot/device-2016-11-11-110118.png)
>![image](https://raw.githubusercontent.com/frendyxzc/XwsdkPlugin/master/screenshot/device-2016-11-11-110150.png)
<br>
<br>

**功能**：

>轻松接入鲜闻内容

**原理**：

>封装鲜闻内容

**用法**：

>1.依赖module - xwview;

>2.Application继承XwBaseApplication，如下：
```java
	public class BaseApplication extends XwBaseApplication {
	}
```

>3.AndroidManifest.xml里配置网络权限，并在application添加name：
```java
	<!-- 网络权限 -->
	<uses-permission android:name="android.permission.INTERNET" />
	<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
	<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />

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
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);

			FrameLayout mParent = (FrameLayout) findViewById(R.id.activity_main);
			initNewsListView(mParent);
		}
	}
```

<br>
<br>
**Todo List**：
>1.开放新闻item点击跳转接口
>2.推荐系统接入
>3.频道管理接入
>4.搜索功能接入
>5....
<br>
<br>
<br>

Contact Us: 3176385478@qq.com
