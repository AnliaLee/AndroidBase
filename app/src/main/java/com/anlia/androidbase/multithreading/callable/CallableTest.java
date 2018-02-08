package com.anlia.androidbase.multithreading.callable;

import java.text.SimpleDateFormat;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by anlia on 2018/2/8.
 */

public class CallableTest {
    public static class TestCallable implements Callable{
        private int ticket = 10;

        @Override
        public String call() throws Exception {
            System.out.println(Thread.currentThread().getName() + "：我买几个橘子去。你就在此地，不要走动" + " 时间：" + getTime());
            Thread.sleep(2000);//模拟买橘子的时间
            return Thread.currentThread().getName() + "：我买完橘子回来了" + " 时间：" + getTime();

//            System.out.println(Thread.currentThread().getName() + "：橘子卖完了" + " 时间：" + getTime());
//            throw new NullPointerException("橘子卖完了");
        }
    }

    public static String getTime(){
        SimpleDateFormat df = new SimpleDateFormat("mm:ss");
        return df.format(System.currentTimeMillis());
    }

    public static void main(String args[]){
        TestCallable callable = new TestCallable();
//        FutureTask<String> futureTask = new FutureTask<String>(callable);
//
//        Thread thread1 = new Thread(futureTask, "爸爸去了第一个摊位");
//        Thread thread2 = new Thread(futureTask, "爸爸去了第二个摊位");
//        Thread thread3 = new Thread(futureTask, "爸爸去了第三个摊位");
//
//        thread1.start();
//        thread2.start();
//        thread3.start();
//
//        System.out.println("儿子站在原地" + " 时间：" + getTime());//验证主线程的执行情况
//        try{
//            System.out.println(futureTask.get());
//            System.out.println("儿子收到橘子" + " 时间：" + getTime());//验证主线程的执行情况
//        }catch (InterruptedException | ExecutionException e){
//            System.out.println("儿子没收到橘子" + " 时间：" + getTime());//验证主线程的执行情况
//        }
        ExecutorService executor = Executors.newCachedThreadPool();
        Future<String> future = executor.submit(callable);

        //或者
        //FutureTask<String> future = new FutureTask<String>(callable);
        //executor.execute(future);
        System.out.println("儿子站在原地" + " 时间：" + getTime());//验证主线程的执行情况
        try{
            System.out.println(future.get());
            System.out.println("儿子收到橘子" + " 时间：" + getTime());//验证主线程的执行情况
        }catch (InterruptedException | ExecutionException e){
            System.out.println("儿子没收到橘子" + " 时间：" + getTime());//验证主线程的执行情况
        }
    }
}
