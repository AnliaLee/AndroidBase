package com.anlia.androidbase.multithreading.asynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.anlia.androidbase.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsyncTaskTestActivity extends AppCompatActivity {
    TextView textShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async_task_test);
        textShow = (TextView) findViewById(R.id.text_show);
    }

    public void clickEvent(View view) {
    	switch (view.getId()) {
            case R.id.btn_start:
                List<String> list = new ArrayList<>();
                list.add("薯条");
                list.add("汉堡");
                list.add("可乐");
                MyAsyncTask asyncTask = new MyAsyncTask();
                asyncTask.execute(list);
                asyncTask.execute(list);
                break;
    	}
    }

    public class MyAsyncTask extends AsyncTask<List<String>,String,String>{
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textShow.setText("餐盘准备好了，开始配餐...");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            textShow.setText("配餐完毕，" + s);
        }

        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
            textShow.setText("未完成配餐，" + s);
        }

        @Override
        protected String doInBackground(List<String>... params) {
            String foods = "已经配好的食物 —— ";
            try {
                for (String str : params[0]){
                    if(str.equals("可乐")){
                        Thread.sleep(500);
                        cancel(true);
                        while (true){
                            /**
                             * cancel方法只是简单把标志位改为true
                             * 最后使用Thread.interrupt去中断线程执行
                             * 但这不能保证可以马上停止任务
                             * 所以需使用isCancelled来判断任务是否被取消
                             */
                            if(isCancelled()){
                                return foods;
                            }
                        }
                    }

                    Thread.sleep(1000);//模拟配餐的时间
                    foods = foods + str + " ";
                    publishProgress(foods);
                    Log.e("白胡子快餐店",foods);
                }
                Thread.sleep(500);
            }catch (Exception e){}
            return foods;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            textShow.setText(values[0]);
        }
    }
}
