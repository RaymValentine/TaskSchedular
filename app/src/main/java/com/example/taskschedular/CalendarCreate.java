package com.example.taskschedular;

import android.app.Activity;
import android.widget.TextView;
import java.util.Calendar;


public class CalendarCreate extends Activity{

    //変数宣言
    public int showYear;   //対象の西暦年
    public int showMonth;  //対象の月
    private int dayCount;   //
    private int startDay;   //先頭曜日
    private int lastDate1;  //月末日付1
    private int lastDate2;  //月末日付2
    private boolean isStart;    //
    private boolean isEnd;      //
    private int x;          //
    private int y;          //
    private String name;    //
    private int resId;   //
    private int len;        //

    private int[][] calendarMatrix = new int[6][7];   //カレンダー情報テーブル

    //カレンダー初期設定
    public void calenderInitial(){
        //初期設定
        Calendar cal = Calendar.getInstance();
        showYear = cal.get(Calendar.YEAR);//現在の年を取得
        showMonth = cal.get(Calendar.MONTH) + 1;//現在の月を取得
        createCalendar();
    }

    //カレンダー作成
    private void createCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        // 年月表示
        TextView textView = (TextView)this.findViewById(R.id.txtYM);
        textView.setText(showYear + "年" + showMonth + "月");
        // 月の初めの曜日を求めます。
        calendar.set(showYear, showMonth - 1, 1); // 引数: 1月: 0, 2月: 1, ...
        startDay = calendar.get(Calendar.DAY_OF_WEEK);//曜日を取得
        // 今月末の日付を求めます。
        calendar.add(Calendar.MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        lastDate1 = calendar.get(Calendar.DATE);//日を取得
        dayCount = 1;
        // 前月末の日付を求めます。
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.DATE, -1);
        lastDate2 = calendar.get(Calendar.DATE);//日を取得
        //初期値セット
        isStart = false;
        isEnd = false;
        //前月末の日付用
        x = 0;
        y = 0;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                //先頭曜日確認
                // startDay: 日曜日 = 1, 月曜日 = 2, ...
                if (!isStart && (startDay - 1) == j) {
                    //日にちセット開始
                    isStart = true;
                    //今月のカレンダーに前月末表示用
                    y = i;
                    x = j;
                    lastDate2 = lastDate2 - (startDay - 2);
                }
                if (isStart) {
                    //終了日まで行ったか
                    calendarMatrix[i][j] = dayCount;
                    //カウント＋１
                    dayCount++;
                    //終了確認
                    if (dayCount > lastDate1) {
                        isEnd = true;
                        //来月初をセット
                        dayCount = 1;
                    }
                }
            }
        }
        //前月末を改めて挿入
        for (int i = 0; i <= y; i++) {
            for (int j = 0; j < x; j++) {
                calendarMatrix[i][j] = lastDate2;
                lastDate2++;
            }
        }
        //TextViewに日付をセット
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                name = "txDay" + String.valueOf(i) + String.valueOf(j);
                resId = getResources().getIdentifier(name, "id", getPackageName());
                TextView textView1 = (TextView) findViewById(resId);
                //
                len = Integer.toString(calendarMatrix[i][j]).length();
                if (len == 1) {
                    textView1.setText(String.valueOf(String.format("%1$2d", calendarMatrix[i][j])) + "\n" + "\n");
                } else {
                    textView1.setText(String.valueOf(calendarMatrix[i][j]) + "\n" + "\n");
                }
            }
        }
    }
}
