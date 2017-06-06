# `TabPager`

![jCenter](https://img.shields.io/badge/jCenter-1.0.6-brightgreen.svg) ![Fragment](https://img.shields.io/badge/Fragment-TabLayout+ViewPager-brightgreen.svg) ![Fragment](https://img.shields.io/badge/Fragment-RadioGroup+ViewPager-brightgreen.svg)

> `TabPager`不仅集成了`TabLayout`和`ViewPager`为顶部选项卡页面，也集成了`RadioGroup`和`ViewPager`为底部导航栏页面，还封装了根据具体页面根据不同的加载状态而显示不同页面的功能，也可以自定义这些页面和其他一些属性。如果某个页面加载数据不成功，切换到其他页面再回来时，框架会自动调用重试加载功能；如果加载成功了，则不再重试加载。

### 1. 运行效果

* 屏幕截图（`gif`录制卡顿，实际运行流畅）：

![ScreenShot](./imgs/screenshot.gif)

* Demo下载体验，[`TabPager`](https://fir.im/tpq72p) ，或者扫描二维码下载

  ![TabPager](./imgs/download_qr.png)


### 2. 依赖

```groovy
dependencies {
  //1.0.3版本之前，不含底部导航栏
  compile 'com.yhy:tpg:latestVersion'
  
  //1.0.5版本及之后，加入了底部导航栏
  compile 'com.yhy.widget:tabnav:latestVersion'
}
```

### 3. 使用说明

* 顶部选项卡页面控件[**TpgView**](./doc/TpgView.md)
* 底部导航栏页面控件[**NavView**](./doc/NavView.md)

### 4. 嵌套使用

> 嵌套使用使用方法跟普通使用一样，唯一需要注意的是创建适配器时的`FragmentManager`，如果是一级`Fragment`的话，需要传入`getSupportFragmentManager()`，否则只能是`getChildFragmentManager()`

```java
//mAdapter = new PagersAdapter(getFragmentManager());
/*
//这里需要用getChildFragmentManager()

getChildFragmentManager()是fragment中的方法, 返回的是管理当前fragment内部子fragments的manager.
getFragmentManager()在activity和fragment中都有.
在activity中, 如果用的是v4 support库, 方法应该用getSupportFragmentManager(),返回的是管理activity中fragments的manager.
在fragment中, 还叫getFragmentManager(), 返回的是把自己加进来的那个manager.
也即, 如果fragment在activity中, fragment.getFragmentManager()得到的是activity中管理fragments的那个manager.如果fragment是嵌套在另一个fragment中, fragment.getFragmentManager()得到的是它的parent的getChildFragmentManager().
总结就是: getFragmentManager()是本级别管理者, getChildFragmentManager()是下一级别管理者.
这实际上是一个树形管理结构.
*/
mAdapter = new PagersAdapter(getChildFragmentManager());
tpgView.setAdapter(mAdapter);
```

---

### 更新日志

- 1.0.6

  - `TpgFragment`加入RT泛型

    > 在`TpgFragment`内部保存`TpgView`或者`NavView`的根`Activity`或者`Fragment`。只需要在设置适配器时传入泛型，并调用`setRoot(RT root)`方法，就可以在每一个子页面获取到根页面的引用，进而调用根页面的方法

    ```java
    //改变一下适配器的getPager(int position)方法
    @Override
    public TpgFragment<TpgActivity> getPager(int position) {
      //创建页面
      TpgFragment<TpgFragment> pager = new PagerA<>();
      //设置根页面
      pager.setRoot(TpgActivity.this);
      return pager;
    }

    //然后，你就可以在PagerA页面中直接调用TpgActivity中的方法了，哈哈~
    ```

  - 在`TpgView`的选项卡栏左边添加说明文字

    > 比如“请选择：”等。。这种`UI`不常见。。不过有些变态设计总会玩些非主流（坑爹啊:sob:）
    >
    > 具体属性设置如下：

    ```xml
    <!--是否显示Tab栏TextView，默认为：GONE-->
    <attr name="text_visible" format="integer">
      <enum name="visible" value="0"/>
      <enum name="invisible" value="4"/>
      <enum name="gone" value="8"/>
    </attr>
    <!--Tab栏TextView内容-->
    <attr name="text_text" format="reference|string"/>
    <!--Tab栏TextView字体颜色，默认为：#aaff4400-->
    <attr name="text_color" format="reference|color"/>
    <!--Tab栏TextView字体大小，默认为：14sp-->
    <attr name="text_size" format="reference|dimension"/>
    <!--Tab栏TextView左侧边距，默认为：8dp-->
    <attr name="text_margin_left" format="reference|dimension"/>
    <!--Tab栏TextView右侧边距，默认为：8dp-->
    <attr name="text_margin_right" format="reference|dimension"/>
    ```

- 1.0.5

  > 加入`NavView`框架

### 句终

> 哈哈。。