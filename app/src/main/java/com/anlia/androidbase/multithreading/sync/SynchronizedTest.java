package com.anlia.androidbase.multithreading.sync;

/**
 * Created by anlia on 2018/1/31.
 */

public class SynchronizedTest {

    public synchronized void test1(){
        try{
            System.out.println("请鸣人影分身 "+Thread.currentThread().getName()+" 吃拉面");
            Thread.sleep(1000);

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("鸣人影分身 "+Thread.currentThread().getName()+" 吃完拉面了");
    }

    public static synchronized void test2(){
        try{
            System.out.println("请鸣人 本体 吃拉面：" + Thread.currentThread().getName());
            Thread.sleep(1000);

        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("鸣人 本体 吃完拉面了：" + Thread.currentThread().getName());
    }

    public class TicketThread extends Thread {
        private int ticket = 10;
        private String name;

        public TicketThread(String name) {
            this.name = name;
        }

        public void run() {
            for (int i = 0; i < 10; i++) {
                if (ticket > 0) {
                    System.out.println(name + "卖了一张票，编号为t" + (ticket--));
                }
            }
        }

    }

    TicketThread t1 = new TicketThread("1号业务员");
    TicketThread t2 = new TicketThread("2号业务员");

    public static void main(String args[]) {
        SynchronizedTest synchronizedTest = new SynchronizedTest();
        synchronizedTest.t1.start();
        synchronizedTest.t2.start();

        /*Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                new SynchronizedTest().test1();
            }
        }, "1号");
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                new SynchronizedTest().test1();
            }
        }, "2号");
        Thread t3 = new Thread(new Runnable() {
            @Override
            public void run() {
                new SynchronizedTest().test1();
            }
        }, "3号");
        t1.start();
        t2.start();
        t3.start();*/

        /*Thread t4 = new Thread(new Runnable() {
            @Override
            public void run() {
//                test2();//test2方法是静态方法，可以直接调用
                new SynchronizedTest().test2();//这里的调用方式仅为了作对照
            }
        },"t4");
        Thread t5 = new Thread(new Runnable() {
            @Override
            public void run() {
                new SynchronizedTest().test2();
            }
        },"t5");
        Thread t6 = new Thread(new Runnable() {
            @Override
            public void run() {
                new SynchronizedTest().test2();
            }
        },"t6");

        t4.start();
        t5.start();
        t6.start();*/
    }


    /*public synchronized void test1(){
        //持有锁的对象为SynchronizedTest的实例对象
    }

    public void test2(){
        synchronized (this){
            //持有锁的对象为SynchronizedTest的实例对象
        }
    }

    private String obj = "obj";
    public void test3(){
        synchronized (obj){
            //持有锁的对象为obj
        }
    }

    public static synchronized void test4(){
        //持有锁的对象为SynchronizedTest的Class对象（SynchronizedTest.class）
    }

    public void test5(){
        synchronized (SynchronizedTest.class){
            //持有锁的对象为SynchronizedTest的Class对象（SynchronizedTest.class）
        }
    }*/
}
