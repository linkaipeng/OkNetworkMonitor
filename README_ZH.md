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

<img src="screenshots/s1.png" width=300/> <img src="screenshots/s2.png" width=300/> <img src="screenshots/s3.png" width=300/>

### 超时提醒，可自定义超时时间，请求链路任意环节超时即可显示通知

<img src="screenshots/s4.png" width=300/> <img src="screenshots/s5.png" width=300/> <img src="screenshots/s6.png" width=300/>


## LICENSE

```
Copyright 2015 linkaipeng

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

<http://www.apache.org/licenses/LICENSE-2.0>

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

```
