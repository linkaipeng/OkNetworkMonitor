#OkNetworkMonitor

A network monitor for okHttp.


## Usage

### Import the lib to your project.

`implementation project(':lib-ok-network-monitor')`

### Add a network `Interceptor` for `OkHttpClient`

```
new OkHttpClient.Builder()
    .addNetworkInterceptor(new OkNetworkMonitorInterceptor())

```

### Add activities to your `AndroidManifest.xml`

```
<activity
    android:name="com.linkaipeng.oknetworkmonitor.ui.NetworkFeedActivity"
    android:screenOrientation="portrait"/>

<activity
    android:name="com.linkaipeng.oknetworkmonitor.ui.NetworkFeedDetailActivity"
    android:screenOrientation="portrait"/>

```


### Start activity

```
NetworkFeedActivity.start(this);
```


## Screenshots

<img src="art/screenshot1.png" width=216/>
<img src="art/screenshot2.png" width=216/>
<img src="art/screenshot3.png" width=216/>


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