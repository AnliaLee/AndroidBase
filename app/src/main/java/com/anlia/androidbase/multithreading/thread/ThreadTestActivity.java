package com.anlia.androidbase.multithreading.thread;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.anlia.androidbase.R;

public class ThreadTestActivity extends AppCompatActivity {
    EditText editNum;
    private int num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_test);
        editNum = (EditText) findViewById(R.id.edit_num);
        editNum.setText("5");
    }

    public void clickEvent(View view) {
    	switch (view.getId()) {
            case R.id.btn_thread:
                TicketThread t1 = new TicketThread("1号业务员");
                TicketThread t2 = new TicketThread("2号业务员");
//                TicketThread t3 = new TicketThread("3号业务员");

                t1.start();
                t2.start();
//                t3.start();
                break;
            case R.id.btn_runnable:
                SellTask sellTask = new SellTask();
                TicketRunnable runnable = new TicketRunnable(sellTask);
                Thread r1 = new Thread(runnable, "1号业务员");
                Thread r2 = new Thread(runnable, "2号业务员");
                Thread r3 = new Thread(runnable, "3号业务员");

                r1.start();
                r2.start();
                r3.start();
                break;
            case R.id.btn_synchronized:
                ReportTask reportTask = new ReportTask();
                ReportRunnable1 runnable1 = new ReportRunnable1(reportTask);
                ReportRunnable2 runnable2 = new ReportRunnable2(reportTask);
                ReportRunnable3 runnable3 = new ReportRunnable3(reportTask);
                ReportRunnable4 runnable4 = new ReportRunnable4(reportTask);

                Thread s1 = new Thread(runnable1);
                Thread s2 = new Thread(runnable2);
                Thread s3 = new Thread(runnable3);
                Thread s4 = new Thread(runnable4);
                s1.start();
                s2.start();
                s3.start();
                s4.start();
                break;
            case R.id.btn_synchronized1:

                break;
    	}
    }

    public class TicketThread extends Thread {
        private int ticket = Integer.valueOf(editNum.getText().toString()) ;
        private String name;

        public TicketThread(String name) {
            this.name = name;
        }

        public void run() {
            for (int i = 0; i < Integer.valueOf(editNum.getText().toString()); i++) {
                if (ticket > 0) {
                    Log.e("T公司",name + "卖了一张票，编号为t" + (ticket--));
                }
            }
        }

    }

    private class SellTask {
        private int ticket = Integer.valueOf(editNum.getText().toString());
        private int ticket1 = Integer.valueOf(editNum.getText().toString());

        public synchronized void sellTicket(){
            if (ticket > 0) {
                try{
                    Thread.sleep(500);
                    Log.e("R公司",Thread.currentThread().getName() + "卖了一张票，编号为r" + (ticket--));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public class TicketRunnable implements Runnable {
        SellTask sellTask;
        public TicketRunnable(SellTask sellTask){
            this.sellTask = sellTask;
        }

        public void run() {
            for (int i = 0; i < Integer.valueOf(editNum.getText().toString()); i++) {
                sellTask.sellTicket();
            }
        }
    }

    /*private class ReportTask {
        public synchronized void report1(){
            Log.e("R公司","1号业务员" + "进办公室");
            try{
                Log.e("R公司","1号业务员" + "开始汇报");
                Thread.sleep(1000);

            }catch (Exception e){
                e.printStackTrace();
            }
            Log.e("R公司","1号业务员" + "汇报完毕");
            Log.e("R公司","1号业务员" + "出办公室");
        }

        public synchronized void report2(){
            Log.e("R公司","2号业务员" + "进办公室");
            try{
                Log.e("R公司","2号业务员" + "开始汇报");
                Thread.sleep(1000);

            }catch (Exception e){
                e.printStackTrace();
            }
            Log.e("R公司","2号业务员" + "汇报完毕");
            Log.e("R公司","2号业务员" + "出办公室");
        }

        public void report3(){
            Log.e("R公司","3号业务员" + "进办公室");
            try{
                Log.e("R公司","3号业务员" + "开始汇报");
                Thread.sleep(1000);

            }catch (Exception e){
                e.printStackTrace();
            }
            Log.e("R公司","3号业务员" + "汇报完毕");
            Log.e("R公司","3号业务员" + "出办公室");
        }
    }*/

    private class ReportTask {
        public void report1(){
            synchronized(this){
                Log.e("R公司","1号业务员" + "进办公室");
                try{
                    Log.e("R公司","1号业务员" + "开始汇报");
                    Thread.sleep(1000);

                }catch (Exception e){
                    e.printStackTrace();
                }
                Log.e("R公司","1号业务员" + "汇报完毕");
                Log.e("R公司","1号业务员" + "出办公室");
            }
        }

        public void report2(){
            synchronized(this){
                Log.e("R公司","2号业务员" + "进办公室");
                try{
                    Log.e("R公司","2号业务员" + "开始汇报");
                    Thread.sleep(1000);

                }catch (Exception e){
                    e.printStackTrace();
                }
                Log.e("R公司","2号业务员" + "汇报完毕");
                Log.e("R公司","2号业务员" + "出办公室");
            }
        }

        public void report3(){
            synchronized(this){
                Log.e("R公司","3号业务员" + "进办公室");
                try{
                    Log.e("R公司","3号业务员" + "开始汇报");
                    Thread.sleep(1000);

                }catch (Exception e){
                    e.printStackTrace();
                }
                Log.e("R公司","3号业务员" + "汇报完毕");
                Log.e("R公司","3号业务员" + "出办公室");
            }
        }

        private String window = "window";
        public void report4(){
            synchronized (window){
                Log.e("R公司","小T" + "进办公室");
            }

        }
    }

    public class ReportRunnable1 implements Runnable {
        ReportTask task;
        public ReportRunnable1(ReportTask task){
            this.task = task;
        }

        public void run() {
            task.report1();
        }
    }

    public class ReportRunnable2 implements Runnable {
        ReportTask task;
        public ReportRunnable2(ReportTask task){
            this.task = task;
        }

        public void run() {
            task.report2();
        }
    }

    public class ReportRunnable3 implements Runnable {
        ReportTask task;
        public ReportRunnable3(ReportTask task){
            this.task = task;
        }

        public void run() {
            task.report3();
        }
    }

    public class ReportRunnable4 implements Runnable {
        ReportTask task;
        public ReportRunnable4(ReportTask task){
            this.task = task;
        }

        public void run() {
            task.report4();
        }
    }
}
