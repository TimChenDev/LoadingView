# LoadingView

[![jitpack](https://jitpack.io/v/TimChenDev/LoadingView.svg)](https://jitpack.io/#TimChenDev/LoadingView)

這是一個載入資料提示的範例, 具備重新載入的介面</br>
LoadingView Demo, with reload button

## Gradle Setup

``` gradle
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.TimChenDev:LoadingView:1.1.1'
}
```

## Preview

![loading](art/loading.png)
![load_error](art/load_error.png)

## How to use

### Initial LoadingView

In layout.xml, cover on another view that use to show data, like RecyclerView.

```xml
<FrameLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent">
    <androidx.recyclerview.widget.RecyclerView
        android:id="@+id/recycler_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
    <com.timchentw.loadingview.LoadingView
        android:id="@+id/loading_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />
</FrameLayout>
```

### Initial reload button listener

set reload button listener, input some reload job, it will trigger when reload button clicked

```kotlin
loadingView.listener = object : LoadingView.OnLoadingViewListener {
    override fun onReload() {
        presenter.loadData() // or viewModel.loadData()
    }
}
```

### Call startLoading function

call startLoading() before you start some data request

``` kotlin
loadingView.startLoading()
presenter.loadData() // or viewModel.loadData()
```

### Call finishLoading function

call finishLoading() when you finishing the work, and give it a result to let loadingView decide which view should be shown

``` kotlin
// true will invisible loadingView
loadingView.finishLoading(true)

// false will show error message and show reload button
loadingView.finishLoading(false)
```

### Custom text and ui color

```xml
<com.timchentw.loadingview.LoadingView
    android:id="@+id/loading_view"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:error_text="目前無法取得資料\n請確認網路狀態後再重新整理"
    app:loading_text="@string/loading"
    app:progress_color="#998800"
    app:refresh_tint="@color/colorPrimary" />
```

## LICENSE

``` text
Copyright 2017 Google

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
