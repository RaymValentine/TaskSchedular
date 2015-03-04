package com.example.taskschedular;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.ButterKnife;


public class MainActivity extends ActionBarActivity  {

    //変数宣言
    private int showYear;       //対象の西暦年
    private int showMonth;      //対象の月
    private int nowYear;
    private int nowMonth;
    private int nowDay;
    private int[][] calendarMatrix = new int[6][7]; // カレンダー情報テーブル
    private int[][] monthFlg = new int[6][7];       // 0:前月　1:今月　2:次月

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);


        ViewPager mViewPager = (ViewPager)findViewById(R.id.viewpager);
//        PagerAdapter mPagerAdapter = new MyPagerAdapter();
//        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setAdapter(new MyPagerAdapter(this));
        mViewPager.setCurrentItem(1, false);    // 初期ページを設定


//        //初期設定
//        Calendar cal = Calendar.getInstance();
//        nowYear         = cal.get(Calendar.YEAR);       //現在の年を取得
//        nowMonth        = cal.get(Calendar.MONTH) + 1;  //現在の月を取得
//        nowDay          = cal.get(Calendar.DATE);       //現在の日を取得
//        showYear        = cal.get(Calendar.YEAR);       //現在の年を取得
//        showMonth       = cal.get(Calendar.MONTH) + 1;  //現在の月を取得
//        createCalendar();

    }


    private class MyPagerAdapter extends PagerAdapter {
        private LayoutInflater mInflter;    // レイアウトを作る
        private Activity mParentActivity;   // アクティビティ

        /**
         * コンストラクタ
         *
         * @param activity
         */
        public MyPagerAdapter(final Activity activity) {
            mInflter = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mParentActivity = activity;
        }

        /**
         * ページを作るときに呼ばれるらしい
         *
         * @param container
         * @param position
         * @return
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            int[] pages = {R.layout.calendar_view0, R.layout.calendar_view1, R.layout.calendar_view2};

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View layout;
            layout = inflater.inflate(pages[position], null);
            ((ViewPager) container).addView(layout);


            // カレンダー表示初期設定
            Calendar cal = Calendar.getInstance();
            nowYear = cal.get(Calendar.YEAR);       //現在の年を取得
            nowMonth = cal.get(Calendar.MONTH) + 1;  //現在の月を取得
            nowDay = cal.get(Calendar.DATE);       //現在の日を取得
            showYear = cal.get(Calendar.YEAR);       //現在の年を取得
            showMonth = cal.get(Calendar.MONTH) + 1;  //現在の月を取得

            int intArgYear;     // 表示する年指定
            int intArgMonth;    // 表示する月指定
            switch (position) {
                case 0: // とりあえず前月表示
                    if (nowMonth == 1) {
                        intArgYear = nowYear - 1;
                        intArgMonth = 12;
                    } else {
                        intArgYear = nowYear;
                        intArgMonth = nowMonth - 1;
                    }
                    createCalendar(layout, intArgYear, intArgMonth);
                    break;

                case 1: // とりあえず今月表示
                    intArgYear = nowYear;
                    intArgMonth = nowMonth;
                    createCalendar(layout, intArgYear, intArgMonth);
                    break;

                case 2: // とりあえず次月表示
                    if (showMonth == 12) {
                        intArgYear = nowYear + 1;
                        intArgMonth = 1;
                    } else {
                        intArgYear = nowYear;
                        intArgMonth = nowMonth + 1;
                    }
                    createCalendar(layout, intArgYear, intArgMonth);
                    break;
            }

            return layout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView((View) object);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
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

    /**
     * カレンダー作成
     * @param view
     */
    private void createCalendar(View view, int year, int month) {

        int dayCount;       //
        int startDay;       //先頭曜日
        int lastDate1;      //月末日付1
        int lastDate2;      //月末日付2
        boolean isStart;    //
        boolean isEnd;      //
        int x;              //
        int y;              //

        monthFlg = new int[6][7];                       // ここでは初期化を行う

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        // 年月表示
        TextView textView = (TextView)view.findViewById(R.id.txtYM);
        textView.setText(year + "年" + month + "月");
        // 月の初めの曜日を求めます。
        calendar.set(year, month - 1, 1);       // 引数: 1月: 0, 2月: 1, ...
        startDay = calendar.get(Calendar.DAY_OF_WEEK);  // 曜日を取得
        // 今月末の日付を求めます。
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        lastDate1 = calendar.get(Calendar.DATE);        // 日を取得
        dayCount = 1;
        // 前月末の日付を求めます。
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        lastDate2 = calendar.get(Calendar.DATE);        // 日を取得
        // 初期値セット
        isStart = false;
        isEnd = false;
        // 前月末の日付用
        x = 0;
        y = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                // 先頭曜日確認
                // startDay: 日曜日 = 1, 月曜日 = 2, ...
                if (!isStart && (startDay - 1) == j) {
                    // 日にちセット開始
                    isStart = true;
                    // 今月のカレンダーに前月末表示用
                    y = i;
                    x = j;
                    lastDate2 = lastDate2 - (startDay - 2);
                }
                if (isStart) {
                    // 終了日まで行ったか
                    calendarMatrix[i][j] = dayCount;
                    if (!isEnd) {
                        monthFlg[i][j] = 1;
                    } else {
                        monthFlg[i][j] = 2;
                    }
                    // カウント＋１
                    dayCount++;
                    // 終了確認
                    if (dayCount > lastDate1) {
                        isEnd = true;
                        // 来月初をセット
                        dayCount = 1;
                    }
                }
            }
        }

        // 前月末を改めて挿入
        for (int i = 0; i <= y; i++) {
            for (int j = 0; j < x; j++) {
                calendarMatrix[i][j] = lastDate2;
                lastDate2++;
            }
        }

        RefreshMonthView(view, year, month); // 月表示を初期化
    }

    /**
     * つき表示を初期化
     * @param view
     */
    private void RefreshMonthView(View view, int year, int month) {
        String name;        //
        int resId;          //
        int intTxtSizeNormalView = 12;      // テキストサイズセット（通常サイズ）

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                name = "txtDay" + String.valueOf(i) + String.valueOf(j);
                resId = getResources().getIdentifier(name, "id", getPackageName());
                TextView textView1 = (TextView)view.findViewById(resId);

                // 日付を設定
                textView1.setText(String.valueOf(String.format("%d ", calendarMatrix[i][j])) + "\n" + "\n");

                // 日曜・土曜は専用のテキストカラーを設定
                if (j == 0) {
                    textView1.setTextColor(Color.RED);
                } else if (j == 6) {
                    textView1.setTextColor(Color.BLUE);
                }

                // テキストの表示位置を設定（右寄せ）
                textView1.setGravity(Gravity.RIGHT);

                // 背景セット// 0:前月　1:今月　2:次月
                if (monthFlg[i][j] == 1) {
                    textView1.setBackgroundResource(R.drawable.background_nowmonth);
                } else {
                    textView1.setBackgroundResource(R.drawable.background_prenex);
                }

                // 今日の背景をセット
                if ((calendarMatrix[i][j] == nowDay)
                        && (monthFlg[i][j] == 1)
                        && (year == nowYear)
                        && (month == nowMonth)) {
                    textView1.requestFocus();
                    textView1.setBackgroundResource(R.drawable.background_today);
                }
                textView1.setTextSize(intTxtSizeNormalView);   // テキストサイズセット（通常サイズ）
            }
        }
    }

}
