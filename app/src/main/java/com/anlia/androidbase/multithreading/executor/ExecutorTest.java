package com.anlia.androidbase.multithreading.executor;

import android.support.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by anlia on 2018/2/8.
 */

public class ExecutorTest {
    private static int taskSize = 10;//任务数
    private static int corePoolSize = 5;//核心线程的数量
    private static int maximumPoolSize = 5;//线程数的最大值
    private static int queueSize = 10;//可储存的任务数

    public static class TestTask implements Runnable {
        public void run() {
            if (taskSize > 0) {
                try{
                    Thread.sleep(500);//模拟开发时间
                    System.out.println(getTime() + getName(Thread.currentThread().getName())
                            + " 完成一个开发任务，编号为t" + (taskSize--)
                    );
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static class PriorityTask implements Runnable,Comparable<PriorityTask>{
        private int priority;

        public PriorityTask(int priority) {
            this.priority = priority;
        }
        @Override
        public void run() {
            if (taskSize > 0) {
                try{
                    Thread.sleep(1000);//模拟开发时间
                    System.out.println(getTime() + getName(Thread.currentThread().getName())
                            + " 完成一个开发任务，编号为t" + (taskSize--) + "， 优先级为：" + priority
                    );
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        /*@Override
        public int compareTo(Object arg) {
            PriorityTask task = (PriorityTask)arg;
            if(this.priority == task.priority){
                return 0;
            }
            return this.priority>task.priority?1:-1;//优先级大的先执行
        }*/

        @Override
        public int compareTo(PriorityTask task) {
            if(this.priority == task.priority){
                return 0;
            }
            return this.priority<task.priority?1:-1;//优先级大的先执行
        }
    }

    public static class DelayTask implements Runnable,Delayed{
        private long finishTime;
        private long delay;

        public DelayTask(long delay){
            this. delay= delay;
            finishTime = (delay + System.currentTimeMillis());//计算出完成时间
        }

        @Override
        public void run() {
            if (taskSize > 0) {
                try{
                    System.out.println(getTime() + getName(Thread.currentThread().getName())
                            + " 完成一个开发任务，编号为t" + (taskSize--) + "， 用时：" + delay/1000
                    );
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

        @Override
        public long getDelay(@NonNull TimeUnit unit) {
            //将完成时间和当前时间作比较，<=0 时说明元素到期需被释放
            return (finishTime - System.currentTimeMillis());
        }

        @Override
        public int compareTo(@NonNull Delayed o) {
            DelayTask temp = (DelayTask) o;
            return temp.delay < this.delay?1:-1;//延迟时间越短优先级越高
        }
    }

    public static String getTime(){
        SimpleDateFormat df = new SimpleDateFormat("mm:ss");
        return df.format(System.currentTimeMillis());
    }

    public static String getName(String threadName){
        String[] temp = threadName.split("-");
        if(Integer.valueOf(temp[3]) <= corePoolSize){
            return "  核心程序员 " + temp[3] + "：";
        }else {
            return "  实习生 " + temp[3] + "：";
        }
    }

    public static void main(String args[]){
        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(
                        corePoolSize,
                        maximumPoolSize,
                        1,
                        TimeUnit.SECONDS,
                        new ArrayBlockingQueue<Runnable>(queueSize)
//                        new LinkedBlockingQueue<Runnable>()
//                        new PriorityBlockingQueue<Runnable>(queueSize)
//                        new DelayQueue()
//                        new SynchronousQueue<Runnable>()

                );
        Random random = new Random();
        TestTask task;
//        PriorityTask task;
//        DelayTask task;
        int size = taskSize;
        for (int i = 0; i < size; i++) {
            task = new TestTask();
            executor.execute(task);
            System.out.println("接到任务 " + i);

//            int p = random.nextInt(100);
//            task = new PriorityTask(p);
//            executor.execute(task);
//            System.out.println("接到任务 " + i + "，优先级为:" + p);

//            long d = 1000 + random.nextInt(10000);
//            task = new DelayTask(d);
//            executor.execute(task);
//            System.out.println("接到任务 " + i + "，预计完成时间为:" + d/1000);
        }
        executor.shutdown();

//        ExecutorService service = Executors.newCachedThreadPool();
//        ExecutorService service = Executors.newFixedThreadPool(corePoolSize);
//        ExecutorService service = Executors.newSingleThreadExecutor();
//        TestTask task;
//        int size = taskSize;
//        for (int i = 0; i < size; i++) {
//            task = new TestTask();
//            service.execute(task);
//            System.out.println("接到任务 " + i);
//        }
//        service.shutdown();

//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(3);
//        Runnable runnable = new Runnable(){
//            @Override
//            public void run() {
//                System.out.println("开始执行任务，时间：" + getTime());
//            }
//        };
//        scheduledExecutorService.schedule(runnable,3,TimeUnit.SECONDS);
//        System.out.println("提交任务，时间：" + getTime());

    }
}
