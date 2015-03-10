package com.example.taskschedular;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by owner on 2015/03/04.
 */
public class MyFragment extends Fragment {

    //変数宣言
    Calendar cal = Calendar.getInstance();
    private int nowYear = cal.get(Calendar.YEAR);           //現在の年を取得
    private int nowMonth = cal.get(Calendar.MONTH) + 1;     //現在の月を取得
    private int nowDay = cal.get(Calendar.DATE);            //現在の日を取得
    private int[][] calendarMatrix = new int[6][7];         // カレンダー情報テーブル
    private int[][] monthFlg = new int[6][7];               // 0:前月　1:今月　2:次月

    @InjectView(R.id.txtInfo)
    TextView mTextView;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.calendar_view, container, false);
        Bundle bundle = getArguments();
        int index = bundle.getInt("INDEX");
        int intArgYear;         // 表示する年指定（とりあえず今年を初期設定）
        int intArgMonth;        // 表示する月指定（とりあえず今月を初期設定）

        ButterKnife.inject(this, view);

        if (index == 1) {       // 今日を表示
            intArgYear = nowYear;
            intArgMonth = nowMonth;

        } else {                // 前月 or 次月を表示
            cal.add(Calendar.MONTH, (index - 1));
            intArgYear = cal.get(Calendar.YEAR);
            intArgMonth = cal.get(Calendar.MONTH) + 1;

        }

        // カレンダー作成
        createCalendar(view, intArgYear, intArgMonth);

        return view;
    }

    @OnClick({ R.id.txtDay00, R.id.txtDay01, R.id.txtDay02, R.id.txtDay03, R.id.txtDay04, R.id.txtDay05, R.id.txtDay06
            , R.id.txtDay10, R.id.txtDay11, R.id.txtDay12, R.id.txtDay13, R.id.txtDay14, R.id.txtDay15, R.id.txtDay16
            , R.id.txtDay20, R.id.txtDay21, R.id.txtDay22, R.id.txtDay23, R.id.txtDay24, R.id.txtDay25, R.id.txtDay26
            , R.id.txtDay30, R.id.txtDay31, R.id.txtDay32, R.id.txtDay33, R.id.txtDay34, R.id.txtDay35, R.id.txtDay36
            , R.id.txtDay40, R.id.txtDay41, R.id.txtDay42, R.id.txtDay43, R.id.txtDay44, R.id.txtDay45, R.id.txtDay46
            , R.id.txtDay50, R.id.txtDay51, R.id.txtDay52, R.id.txtDay53, R.id.txtDay54, R.id.txtDay55, R.id.txtDay56
    })
    void clickTxtDay(TextView textView) {
        String strSetText = textView.getText().toString().trim();

        mTextView.setText(String.valueOf(String.format("%S日をタップしました。", strSetText)));
        CustomToast.makeText(getActivity(), "タップ", 500).show();             // トースト表示
    }
//    void clickTxtDay(TextView textView) {
//        Float fTxtSizeSelectView = 25.00F;             // テキストサイズセット（選択サイズ）
//        TextView txtInfo = (TextView)getActivity().findViewById(R.id.txtInfo);
//        txtInfo.setText(textView.getText().toString() + "日を選択");
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, fTxtSizeSelectView);    // テキストサイズセット（dp）
//        textView.setBackgroundResource(R.drawable.background_selectday);
//    }


    /**
     *  カレンダー作成
     * @param view
     * @param year
     * @param month
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
        float fTxtSizeYM = 18.00F;      // テキストサイズセット（年月）

        monthFlg = new int[6][7];                       // ここで初期化を行う

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        // 年月表示
        TextView textView = (TextView)view.findViewById(R.id.txtYM);
        textView.setText(year + "年" + month + "月");

        // テキストサイズを設定（dp）
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, fTxtSizeYM);

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
        float fTxtSizeNormalDayView = 20.00F;      // テキストサイズセット（通常サイズ）

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                name = "txtDay" + String.valueOf(i) + String.valueOf(j);
                resId = getResources().getIdentifier(name, "id", getActivity().getPackageName());
                TextView textView1 = (TextView)view.findViewById(resId);

                // 日付を設定
//                textView1.setText(String.valueOf(String.format("%d ", calendarMatrix[i][j])) + "\n" + "\n");
                textView1.setText(String.valueOf(String.format("%d ", calendarMatrix[i][j])));

                // 日曜・土曜は専用のテキストカラーを設定
                if (j == 0) {
                    textView1.setTextColor(Color.RED);
                } else if (j == 6) {
                    textView1.setTextColor(Color.BLUE);
                }

                // テキストサイズを設定（dp）
                textView1.setTextSize(TypedValue.COMPLEX_UNIT_DIP, fTxtSizeNormalDayView);

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
            }
        }
    }

}
