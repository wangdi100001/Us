package com.example.center.thread;

import lombok.Getter;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Thread03 {

    public static void main(String[] args) throws InterruptedException {

        MyResource myResource = new MyResource(new ArrayBlockingQueue<String>(1));
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+",生产线程启动");
            try {
                myResource.produce();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"Pro").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+",消费线程启动");
            try {
                myResource.consumer();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },"Con").start();

        TimeUnit.SECONDS.sleep(5);
        System.out.println();
        System.out.println();
        System.out.println();


        myResource.stop();
    }

}


class MyResource {

    //定义消息全局控制开关
    private volatile boolean flag = true;
    //定义一个队列
    private BlockingQueue<String> blockingDeque = null;
    //定义可变变量
    AtomicInteger atomicInteger = new AtomicInteger();

    public MyResource(BlockingQueue<String> blockingDeque) {
        this.blockingDeque = blockingDeque;
        System.out.println("当前类名字："+blockingDeque.getClass().getName());
    }

    //生产者
    public void produce() throws InterruptedException {
        boolean resultValue;
        String data = null;
        while (flag) {
            data = atomicInteger.incrementAndGet()+"";
            resultValue = blockingDeque.offer(data,2, TimeUnit.SECONDS);
            if(resultValue){
                System.out.println(Thread.currentThread().getName()+",生产"+data+"成功");
            }else{
                System.out.println(Thread.currentThread().getName()+",生产"+data+"失败");
            }
            TimeUnit.MILLISECONDS.sleep(1000);
        }
        System.out.println("大老板叫停，生产结束");
    }

    //消费
    public void consumer() throws InterruptedException {

        boolean resultValue;
        String result = null;
        while (flag) {
            result = blockingDeque.poll(2l,TimeUnit.SECONDS);
            if(result == null || result.equalsIgnoreCase("")) {
                //没有消息消费，暂停消费
                flag = false;
                System.out.println(Thread.currentThread().getName()+",没有取到蛋糕,2s后停止消费");
                return;
            }
            System.out.println(Thread.currentThread().getName()+",消费"+result+"成功");
        }
    }

    //停止
    public void stop() throws InterruptedException {
        this.flag = false;
    }
}


