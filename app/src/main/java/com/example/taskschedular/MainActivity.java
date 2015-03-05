package com.example.taskschedular;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;


public class MainActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初期データ
        ArrayList<Integer> items = new ArrayList<Integer>();
        items.add(0);
        items.add(1);
        items.add(2);

        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addAll(items);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        viewPager.setOnPageChangeListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
            ViewPager viewPager = (ViewPager)findViewById(R.id.viewpager);
            MyPagerAdapter adapter = (MyPagerAdapter) viewPager.getAdapter();

            ArrayList<Integer> indexes = adapter.getAll();

            int currentPage = viewPager.getCurrentItem();
            if( currentPage != 0 && currentPage != indexes.size() - 1){
                //最初でも最後のページでもない場合処理を抜ける
                return;
            }

            int nextPage = 0;
            if(currentPage == 0){
                //最初のページに到達
                nextPage = 1;
                indexes.add(0, indexes.get(0) - 1);
                //1ページ目は既に存在するため、Fragmentを全て破棄する
                adapter.destroyAllItem(viewPager);
                adapter.notifyDataSetChanged();
            }else if(currentPage == indexes.size() - 1){
                //最後のページに到達
                nextPage = currentPage;
                indexes.add(indexes.get(indexes.size() - 1) + 1);
            }
            adapter.addAll(indexes);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(nextPage);
        }
    }



//    @OnClick(R.id.btnNextMonth)
//    void clickNextMonth() {
//        if (showMonth == 12)
//        {
//            showYear  = showYear + 1;
//            showMonth = 1;
//        }
//        else
//        {
//            showMonth = showMonth + 1;
//        }
//        createCalendar();
//    }
//
//    @OnClick(R.id.btnPreviousMonth)
//    void clickPreviousMonth() {
//        if (showMonth == 1) {
//            showYear = showYear - 1;
//            showMonth = 12;
//        } else {
//            showMonth = showMonth - 1;
//        }
//        createCalendar();
//    }
//
//    @OnClick({ R.id.txtDay00, R.id.txtDay01, R.id.txtDay02, R.id.txtDay03, R.id.txtDay04, R.id.txtDay05, R.id.txtDay06
//            , R.id.txtDay10, R.id.txtDay11, R.id.txtDay12, R.id.txtDay13, R.id.txtDay14, R.id.txtDay15, R.id.txtDay16
//            , R.id.txtDay20, R.id.txtDay21, R.id.txtDay22, R.id.txtDay23, R.id.txtDay24, R.id.txtDay25, R.id.txtDay26
//            , R.id.txtDay30, R.id.txtDay31, R.id.txtDay32, R.id.txtDay33, R.id.txtDay34, R.id.txtDay35, R.id.txtDay36
//            , R.id.txtDay40, R.id.txtDay41, R.id.txtDay42, R.id.txtDay43, R.id.txtDay44, R.id.txtDay45, R.id.txtDay46
//            , R.id.txtDay50, R.id.txtDay51, R.id.txtDay52, R.id.txtDay53, R.id.txtDay54, R.id.txtDay55, R.id.txtDay56
//    })
//    void clickTxtDay(TextView textView) {
//        int intTxtSizeSelectlView = 18;             // テキストサイズセット（選択サイズ）
//
//        RefreshMonthView();                             // 月表示を初期化
//
//        textView.setTextSize(intTxtSizeSelectlView);    // テキストサイズセット（選択サイズ）
//        textView.requestFocus();
//        textView.setBackgroundResource(R.drawable.background_selectday);
//    }


}
