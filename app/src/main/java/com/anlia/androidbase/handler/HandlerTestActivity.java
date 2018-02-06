package com.anlia.androidbase.handler;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.anlia.androidbase.R;

public class HandlerTestActivity extends AppCompatActivity {
    TextView textShow;

    private HandlerThread handlerThread;
    private Handler handler2;

    private static final String TAG = "HandlerTestActivity";
    private static final int CODE_TEST_ONE = 101;
    private static final int CODE_TEST_TWO = 102;
    private static final int CODE_TEST_THREE = 103;
    private static final int CODE_TEST_FOUR = 104;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handler_test);
        textShow = (TextView) findViewById(R.id.text_show);

        /*TestThread testThread = new TestThread();
        testThread.start();

        while (true){//保证testThread.looper已经初始化
            if(testThread.looper!=null){
                handler2 = new Handler(testThread.looper){
                    @Override
                    public void handleMessage(Message msg) {//子线程收到消息后执行
                        switch (msg.what){
                            case CODE_TEST_FOUR:
                                Log.e(TAG,"收到主线程发送的消息");
                                break;
                        }
                    }
                };

                handler2.sendEmptyMessage(CODE_TEST_FOUR);//在主线程中发送消息
                break;
            }
        }*/
        handlerThread = new HandlerThread("MyHandlerThread");
        handlerThread.start();
        handler2 = new Handler(handlerThread.getLooper()){
            @Override
            public void handleMessage(Message msg) {//子线程收到消息后执行
                switch (msg.what){
                    case CODE_TEST_FOUR:
//                        Log.e(TAG,"收到主线程发送的消息");
                        Log.e(TAG,"收到另一个子线程发送的消息");
                        break;
                }
            }
        };
//        handler2.sendEmptyMessage(CODE_TEST_FOUR);//在主线程中发送消息

        Thread testThread = new Thread(new Runnable() {
            @Override
            public void run() {
                handler2.sendEmptyMessage(CODE_TEST_FOUR);//在另一个子线程中发送消息
            }
        });
        testThread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handlerThread.quit();
    }

    public void clickEvent(View view) {
    	switch (view.getId()) {
            case R.id.btn_start:
                new Thread(new TestRunnable()).start();
                break;
    	}
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case CODE_TEST_ONE:
                    textShow.setText("开始刺探军情...");
                    break;
                case CODE_TEST_TWO:
                    textShow.setText("情报收集完毕...");
                    break;
                case CODE_TEST_THREE:
                    textShow.setText("发起总攻！");
                    break;
            }
        }
    };

    private class TestRunnable implements Runnable{
        @Override
        public void run() {
            try {
                handler.sendEmptyMessage(CODE_TEST_ONE);

//                你也可以这样发送消息
//                Message message = Message.obtain();
//                message.what = CODE_TEST_ONE;
//                handler.sendMessage(message);

//                或者
//                message.sendToTarget();

                Thread.sleep(2000);
                handler.sendEmptyMessage(CODE_TEST_TWO);

                Thread.sleep(2000);
                handler.sendEmptyMessage(CODE_TEST_THREE);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    private class TestThread extends Thread{
        private Looper looper;

        @Override
        public void run() {
            super.run();
            Looper.prepare();//创建该子线程的Looper实例
            looper = Looper.myLooper();//取出该子线程的Looper实例
            Looper.loop();//开始循环
        }
    }

    private class mHandlerThread extends HandlerThread{
        public mHandlerThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            super.run();
        }
    }
}
