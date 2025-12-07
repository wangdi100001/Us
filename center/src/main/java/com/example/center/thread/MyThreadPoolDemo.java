package com.example.center.thread;

import java.util.concurrent.*;

public class MyThreadPoolDemo {

    public static void main(String[] args) {

//        useExecutorServicedDemo();
        customizeExecutorServicePool();

    }

    /**
     * 自定义线程池
     */
    private static void customizeExecutorServicePool() {
        Runtime runtime = Runtime.getRuntime();
        System.out.println(runtime.availableProcessors());

//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExecutorService executorService = new ThreadPoolExecutor(2, 5,
                1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),Executors.defaultThreadFactory(),new ThreadPoolExecutor.CallerRunsPolicy());

        /*new ThreadPoolExecutor.AbortPolicy();
        new ThreadPoolExecutor.DiscardOldestPolicy();
        new ThreadPoolExecutor.CallerRunsPolicy();
        new ThreadPoolExecutor.DiscardPolicy();*/

        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            executorService.execute(()-> System.out.println(Thread.currentThread().getName()+",正在执行:"+ finalI));
        }
    }

    /**
     * 使用线程池
     */
    private static void useExecutorServicedDemo() {
        Runtime runtime = Runtime.getRuntime();
        System.out.println(runtime.availableProcessors());

//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            executorService.execute(()-> System.out.println(Thread.currentThread().getName()+",正在执行:"+ finalI));
        }
    }

}
