##   [English ReadMe](https://github.com/linkaipeng/OkNetworkMonitor/blob/master/README_EN.md)


# OkNetworkMonitor

一个 OKHttp 抓包工具，可以实现在手机上面的抓包，可以方便平时开发中的调试。


- 抓包：可以看到 请求头、响应头、响应数据 等；
- 可复制请求为 CURL 格式，方便调试、协作；
- 请求全链路耗时监控，并可自定义超时提醒；


## 使用

### 导入

`implementation project(':lib-ok-network-monitor')`

### 抓包

```

new OkHttpClient.Builder()
    .addNetworkInterceptor(new OkNetworkMonitorInterceptor())

```

### 链路监控

```

new OkHttpClient.Builder()
    .eventListenerFactory(NetworkEventListener.Companion.getFACTORY())

```

### 入口

#### 1.跳到抓包页面

```
NetworkFeedActivity.start(this);
```

#### 2.或者桌面快捷入口

<img src="screenshots/screenshot4.png" width=250/>



## 截图

### 请求详情，支持复制为 curl 格式

<img src="screenshots/s1.jpg" width=200/> <img src="screenshots/s2.jpg" width=200/> <img src="screenshots/s3.jpg" width=200/>

### 超时提醒，可自定义超时时间，请求链路任意环节超时即可显示通知

<img src="screenshots/s4.jpg" width=200/> <img src="screenshots/s5.jpg" width=200/> <img src="screenshots/s6.jpg" width=200/>


## LICENSE

MIT
