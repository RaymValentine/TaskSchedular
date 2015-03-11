package com.example.taskschedular;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by owner on 2015/03/11.
 */
public class CustomToast extends AsyncTask<String, Integer, Integer> implements Runnable{
    private Toast toast = null;
    private long duration = 0;
    private Handler handler = new Handler();

    public static CustomToast makeText (Context context, int resId, long duration){
        CustomToast ct = new CustomToast();
        ct.toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
        ct.duration = duration;

        return ct;
    }

    public static CustomToast makeText (Context context, CharSequence text, long duration){
        CustomToast ct = new CustomToast();
        ct.toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        ct.duration = duration;

        return ct;
    }

    public void show() {
        if(duration > 2000){
            for (int i = 0; i < duration - 2000; i += 2000) {
                handler.postDelayed(this, i);
            }
            handler.postDelayed(this, duration - 2000);
        }else{
            this.execute();
        }
    }

    public void run() {
        toast.show();
    }

    @Override
    protected Integer doInBackground(String... params) {
        try {
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    protected void onPreExecute() {
        toast.setGravity(Gravity.CENTER, 0, 0);     // 中央表示
        toast.show();
    }

    @Override
    protected void onPostExecute(final Integer i) {
        toast.cancel();
    }
}