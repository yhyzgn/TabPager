# `NavView`

> 底部导航栏布局控件
>
> 声明：下文中的`TpgFragment`一律指框架提供的`TpgFragment`或者自定义实现`PagerFace<RT>`接口的`Fragment`。

### 1. 简单用法

- 第一步，再布局文件中使用控件

  ```xml
  <com.yhy.tpg.widget.NavView
      android:id="@+id/tv_content"
      android:layout_width="match_parent"
      android:layout_height="match_parent" />
  ```

- 第二步，设置底部导航元素‘

  ```java
  private static final List<NavTab> TAB_LIST = new ArrayList<>();
  ...
    
    TAB_LIST.add(new NavTab("首页", R.drawable.tab_home_selector));
    TAB_LIST.add(new NavTab("贷款", R.drawable.tab_loan_selector));
    TAB_LIST.add(new NavTab("通知", R.drawable.tab_notice_selector));
    TAB_LIST.add(new NavTab("我的", R.drawable.tab_mine_selector));

  ...
  ```

- 第二步，给`NavView`设置适配器（“页面参数”在扩展中介绍）

  ```java
  //获取到布局中的控件
  bvContent = (NavView) findViewById(R.id.bv_content);

  //设置适配器
  mAdapter = new ContentAdapter(getSupportFragmentManager(), TAB_LIST, mConfig);
  bvContent.setAdapter(mAdapter);

  //内部类中定义适配器
  private class ContentAdapter extends NavAdapter {
    public ContentAdapter(FragmentManager fm, List<NavTab> tabList, PagerConfig config) {
      super(fm, tabList, config);
    }

    @Override
    public TpgFragment getPager(int position) {
      Bundle args = new Bundle();
      args.putBoolean("isHybrid", false);
      args.putBoolean("firstPage", position == 0);
      TpgFragment fragment = new NavPager();
      fragment.setArguments(args);
      return fragment;
    }
  }
  ```

- 第四步，在具体页面类必须继承自`TpgFragment`，实现抽象方法`getSuccessView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)`，并返回加载成功时的页面（以`NavPager.java`为例）

  ```java
  public class NavPager extends TpgFragment {
    ...
      
    @Override
    protected View getSuccessView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      TextView tv = new TextView(getContext());
      tv.setText("Nav页面加载成功");
      tv.setTextColor(Color.RED);
      tv.setTextSize(32);
      tv.setGravity(Gravity.CENTER);
      //返回加载成功时的页面
      return tv;
    }
    
    ...
  }
  ```

- 第五步，实现抽象方法`initData()`，将数据请求部分的操作放在该方法中，便于`NavView`获取到数据加载结果状态。但是加载结果必须要用父类的成员变量`mRltHandler`来操作...

  ```java
  @Override
  protected void initData() {
    //请求服务器数据
    getDataFromServer();
  }

  @Override
  protected void initListener(){
    //初始化一些事件监听
  }

  //模拟请求服务器数据过程
  private void getDataFromServer() {
    //用来产生一个随机的状态
    final Random random = new Random();
    new Thread() {
      @Override
      public void run() {
        //模拟网络加载延迟
        SystemClock.sleep(3000);

        //数据加载结束后，需要手动刷新页面状态
        int temp = random.nextInt(3);
        switch (temp) {
          case 0:
            //如果加载成功
            tpgSuccess();
            break;
          case 1:
            //加载失败
            tpgError();
            break;
          case 2:
            //数据为空
            tpgEmpty();
            break;
          default:
            break;
        }
      }
    }.start();
  }
  ```

  以上就是简单用法的详细步骤 :joy::joy:

------

### 3. 扩展

- 自定义属性

  > 在布局文件中自行定义

  | 属性名                   | 属性说明                         | 默认值       |
  | :----------------------- | :------------------------------- | :----------- |
  | `nav_bg_color`           | 导航栏背景色                     | 透明         |
  | `nav_height`             | 导航栏高度                       | `48dp`       |
  | `nav_bg_img`             | 导航栏背景图片                   | 无           |
  | `nav_text_default_color` | 普通状态下选项字体颜色           | `#000000`    |
  | `nav_text_checked_color` | 选中状态下选项字体颜色           | `#000000`    |
  | `nav_bg_checked_color`   | 选中状态下选项背景颜色           | 透明         |
  | `nav_bg_checked_img`     | 选中状态下选项背景图片           | 无           |
  | `nav_divider_line_color` | 导航栏与内容页面之间的分割线颜色 | 透明，不显示 |
  | `nav_scroll_able`        | 是否可滑动                       | `true`       |
  | `nav_badge_text_color`   | 徽章字体颜色                     | `#ffffffff`  |
  | `nav_badge_bg_color`     | 徽章背景颜色                     | `#ffff2200`  |
  | `nav_badge_drag_enable`  | 徽章是否可拖拽                   | `false`      |

- 页面配置参数（上文提到过）

  > 框架已提供默认的加载中页面、错误页面和空数据页面，但是可能这些都不太适合你，你可以通过该参数配置自己想要的这些页面。该参数只在当前`TpgView`中有效。
  >
  > 注意：该参数必须在设置适配器前配置，通过适配器构造方法设置。

  ```java
  //页面配置，只在当前TpgView有效
  private PagerConfig mConfig;

  //配置页面参数
  mConfig = new PagerConfig();
  //设置空页面的资源id和重试按钮资源id
  mConfig.setEmptyViewResId(R.layout.layout_view_empty, R.id.tv_retry);

  //设置适配器，传入页面配置参数
  mAdapter = new PagersAdapter(getSupportFragmentManager(), mConfig);
  tvContent.setAdapter(mAdapter);
  ```

- 单独页面的页面配置

  > 不仅可以配置`TpgView`范围的页面配置，也可以单独为某一个页面配置相关页面，只需要重写父类的相应方法，并返回页面即可。以配置某个页面的加载中页面为例。

  ```java
  @Override
  protected View getLoadingView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    //只属于B页面的加载中页面
    return inflater.inflate(R.layout.layout_view_loading_b, container, false);
  }
  ```


- 设置未读消息显示及其拖拽消失

  ```java
  bvContent.showCirclePointBadge(0);
  bvContent.showTextBadge(1, "2");
  bvContent.setOnDismissListener(1, new BadgeInterface.OnDismissBadgeListener() {
    @Override
    public void onDismiss() {
      ToastUtils.shortToast("消失了");
    }
  });

  ...
  //详细方法请参考文章末尾
  ```

- 重试加载

  > 框架提供的默认页面已经处理了重试加载功能，但是如果你是自定义的页面（通过以上两种方法），可能你也需要重试加载数据功能，比如“点击按钮重试”之类的功能。只需要在具体需要重试加载的地方调用方法`shouldLoadData()`即可。

  ```java
  retryBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
      //重试加载数据
      shouldLoadData();
    }
  });
  ```

- 对外事件接口

  > 已将`ViewPager`的页面滑动事件和右上角扩展按钮的点击事件提供对外接口

  ```java
  //扩展按钮点击事件
  tvContent.setOnExpandListener(new TpgView.OnExpandListener() {
    @Override
    public void onExpand(View view) {
    }
  });

  //页面滑动事件
  tvContent.setOnPageChangedListener(new TpgView.OnPageChangedListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int
                               positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
  });
  ```

- 重新加载某页数据

  > 也许这个功能是没用的:flushed::flushed:为什么这么说呢？如果在某个页面中，你需要重新加载数据，可以直接调用你加载数据的方法即可，比如`getDataFromServer()`方法，根本不需要框架提供的重新加载数据方法。不过，如果想要在这些页面之外重新加载某个页面的数据的话（比如点击了右上角扩的展按钮之后，一般是要选一些查询条件，然后在重新查询当前页面的数据），就不太好办了:sob::sob:因为你需要调用到具体页面的`getDataFromServer()`方法，而你能想到的方法应该是把所有页面缓存到一个集合中，需要重新加载某页数据时，再取出相应页面，然后调用重新加载方法。当然，这也不失为一种好方法，不过，个人觉得框架中也应该提供默认的重新加载数据的方法，于是才有了该功能。
  >
  > 如何实现呢？？
  >
  > 只需要在具体页面中重写`reloadDate(Bundle args)`方法，实现重新加载数据的逻辑，然后在需要出发重新加载操作的地方调用以下方法即可。
  >
  > 重新加载某个页面的数据：`reloadDataForPager(int index, Bundle args)`
  >
  > ​	参数：`index`		当前页面索引
  >
  > ​		   `args` 		重新加载时，可能需要的参数
  >
  > 重新加载当前页面数据：`reloadDataForCurrentPager(Bundle args)`
  >
  > ​	参数： `args` 		重新加载时，可能需要的参数

  ```java
  //在某个页面内时，重写父类方法即可
  @Override
  public void reloadDate(Bundle args) {
    String temp = args.getString("args");
    ToastUtils.shortToast(temp + "页面重新加载数据");

    //请求数据
    getDataFromServer();
  }

  //在页面以外时，通过适配器调用重新加载数据方法
  if (null != mAdapter) {
    //通过适配器重新加载当前页面的数据
    Bundle args = new Bundle();
    args.putString("args", TABS[tvContent.getCurrentPager()]);
    mAdapter.reloadDataForCurrentPager(args);
  }
  ```

- 未读消息徽章控件显示及其详细方法的接口

  ```java
  public interface BadgeInterface {

      /**
       * 显示圆点徽章
       *
       * @param index Tab的索引
       */
      void showCirclePointBadge(int index);

      /**
       * 显示文字徽章
       *
       * @param index     Tab的索引
       * @param badgeText 显示的文字
       */
      void showTextBadge(int index, String badgeText);

      /**
       * 隐藏徽章
       *
       * @param index Tab的索引
       */
      void dismissBadge(int index);

      /**
       * 显示图像徽章
       *
       * @param index  Tab的索引
       * @param bitmap 图标
       */
      void showDrawableBadge(int index, Bitmap bitmap);

      /**
       * 是否显示徽章
       *
       * @param index Tab的索引
       * @return 是否显示徽章
       */
      boolean isShowBadge(int index);

      /**
       * 徽章消失的回调方法
       *
       * @param index    Tab的索引
       * @param listener 回调事件
       */
      void setOnDismissListener(int index, OnDismissBadgeListener listener);

      /**
       * 徽章消失事件回调接口
       */
      interface OnDismissBadgeListener {
          void onDismiss();
      }
  }
  ```

------------

