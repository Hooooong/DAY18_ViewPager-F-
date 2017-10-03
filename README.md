# ViewPager(Fragment 사용)

### 설명
____________________________________________________

![ViewPager(F)](https://github.com/Hooooong/DAY18_ViewPager-F-/blob/master/image/ViewPager.gif)

- Fragment 를 사용한 ViewPager, TabLayout

### KeyPoint
____________________________________________________

- ViewPager 란?

  - ViewPager 는 화면전환 없이 좌우 Swipe 를 통해 효율적으로 페이지 전환을 할 수 있는 Widget 이다.

  - ViewPager 만 독립적으로 사용할 수 있지만, 대부분 TabLayout 과 함께 사용한다.

- Adpater 구현

  - Fragment 로 구현하기 위해서는 `FragmentStatePagerAdapter` 를 상속받아 구현한다.

  - `getItem()` 과 `getCount()` 를 재정의해야 한다. `getItem()` 은 BaseAdapter의 `getView()` 와 유사하다.

  - `getItem()` 는 Swipe 할 때 최대 3번, 최소 2번이 호출이 된다. 예를 들면 2번 페이지를 보여줄 때, 1, 2, 3 페이지를 한번에 호출해 메모리에 올리기 떄문에 총 3번 호출이 된다.

  ```java
  // ViewPager 에 보일 View의 갯수
  private static final int COUNT = 4;

  // 하나의 View를 보여줄 때 ViewPager 는 3개의 View 를 메모리에 올려두기 때문에 호출이 여러번 된다.
  @Override
  public Fragment getItem(int position) {
      switch (position){
          case 1:
              return new TwoFragment();
          case 2:
              return new ThreeFragment();
          case 3:
              return new FourFragment();
          default:
              return new OneFragment();
          // 그냥 New 로 생성을 하게 되면
          // 마지막에 참조하지 않으면 GC 에서는 삭제 처리가 된다.
      }
  }

  @Override
  public int getCount() {
      return COUNT;
  }
  ```

- ViewPager, TabLayout 사용법

  - ViewPager 는 ListView, RecyclerView 와 유사하게 Adapter 를 상속받아 구현한다.

  ```java
  private void setViewPager() {
      viewPager = (ViewPager)findViewById(R.id.viewPager);
      CustomAdapter customAdapter = new CustomAdapter(getSupportFragmentManager());
      viewPager.setAdapter(customAdapter);
  }
  ```

  - ViewPager 와 TabLayout 을 상호작용시키기 위해 `addOnTabSelectedListener` 와 `addOnPageChangeListener` 를 구현한다.

  ```java
  private void setListener() {
      // TabLayout 을 ViewPager 에 연결
      tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
      // ViewPager 에 변경사항을 TabLayout 에 전달
      viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
  }
  ```
### Code Review
____________________________________________________

- MainActivity.java

  - ViewPager 와 TabLayout 의 정의와 Listener 를 설정해 주는 class 이다.

  ```java
  public class MainActivity extends AppCompatActivity {

      ViewPager viewPager;
      TabLayout tabLayout;

      @Override
      protected void onCreate(Bundle savedInstanceState) {
          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_main);

          setTabLayout();
          setViewPager();
          setListener();
      }

      private void setListener() {
          // TabLayout 을 ViewPager 에 연결
          tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
          // ViewPager 에 변경사항을 TabLayout 에 전달
          viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
      }

      private void setTabLayout(){
          tabLayout = (TabLayout)findViewById(R.id.tabLayout);
          tabLayout.addTab( tabLayout.newTab().setText("One") );
          tabLayout.addTab( tabLayout.newTab().setText("Two") );
          tabLayout.addTab( tabLayout.newTab().setText("Three") );
          tabLayout.addTab( tabLayout.newTab().setText("Four") );
      }

      private void setViewPager() {
          viewPager = (ViewPager)findViewById(R.id.viewPager);
          CustomAdapter customAdapter = new CustomAdapter(getSupportFragmentManager());
          viewPager.setAdapter(customAdapter);
      }
  }
  ```

- CustomAdapter.java

  - FragmentStatePager 를 상속받아 구현하는 Adapter class 이다.

  ```java
  public class CustomAdapter extends FragmentStatePagerAdapter {

      private static final int COUNT = 4;

      public CustomAdapter(FragmentManager fm) {
          super(fm);
      }

      @Override
      public Fragment getItem(int position) {
          switch (position){
              case 1:
                  return new TwoFragment();
              case 2:
                  return new ThreeFragment();
              case 3:
                  return new FourFragment();
              default:
                  return new OneFragment();
              // 그냥 New 로 생성을 하게 되면
              // 마지막에 참조하지 않으면 GC 에서는 삭제 처리가 된다.
          }
      }

      @Override
      public int getCount() {
          return COUNT;
      }
  }
  ```

- One ~ FourFragment.java

  - ViewPager 의 상세 페이지인 Fragment class 이다.

  ```java
  public class OneFragment extends Fragment {
      public OneFragment() {
          // Required empty public constructor
      }
      @Override
      public View onCreateView(LayoutInflater inflater, ViewGroup container,
                               Bundle savedInstanceState) {
          // Inflate the layout for this fragment
          return inflater.inflate(R.layout.fragment_one, container, false);
      }

  }
  ```

- activity_main.xml

  ```xml
  <?xml version="1.0" encoding="utf-8"?>
  <android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
      xmlns:app="http://schemas.android.com/apk/res-auto"
      xmlns:tools="http://schemas.android.com/tools"
      android:layout_width="match_parent"
      android:layout_height="match_parent"
      tools:context="com.hooooong.tablayout.MainActivity">

      <android.support.v4.view.ViewPager
          android:id="@+id/viewPager"
          android:layout_width="0dp"
          android:layout_height="0dp"
          app:layout_constraintBottom_toBottomOf="parent"
          app:layout_constraintLeft_toLeftOf="parent"
          app:layout_constraintRight_toRightOf="parent"
          app:layout_constraintTop_toBottomOf="@+id/tabLayout"/>

      <android.support.design.widget.TabLayout
          android:id="@+id/tabLayout"
          android:layout_width="0dp"
          android:layout_height="50dp"
          app:layout_constraintLeft_toLeftOf="parent"
          app:layout_constraintRight_toRightOf="parent"
          app:layout_constraintTop_toTopOf="parent">

          <!-- Tab 메뉴는 Code 로 추가할 수 있다. -->
          <!--<android.support.design.widget.TabItem
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              android:text="Left" />

          <android.support.design.widget.TabItem
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              android:text="Center" />

          <android.support.design.widget.TabItem
              android:layout_width="wrap_content"
              android:layout_height="wrap_content"
              android:text="Right" />-->
      </android.support.design.widget.TabLayout>
  </android.support.constraint.ConstraintLayout>
  ```

-  fragment_one~four.xml

    ```xml
    <FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context="com.hooooong.tablayout.OneFragment">

        <!-- TODO: Update blank fragment layout -->
        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_gravity="center"
            android:textSize="20dp"
            android:textStyle="bold"
            android:text="@string/title_one" />

    </FrameLayout>
    ```
