package com.example.center.thread;

import java.util.concurrent.*;

public class MyThreadPoolDemo {

    public static void main(String[] args) {

//        useExecutorServicedDemo();
        customizeExecutorServicePool();
        scheduledExecutorServicePoll();
    }

    /**
     * 调度线程池
     */
    private static void scheduledExecutorServicePoll() {
        // 创建调度线程池（核心线程数为1）
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // 延迟1秒执行一次任务
        scheduler.schedule(() -> System.out.println("延迟1秒执行"), 1, TimeUnit.SECONDS);

        // 延迟2秒后，每3秒执行一次任务（固定频率）
        scheduler.scheduleAtFixedRate(() -> {
            System.out.println("固定频率执行：" + System.currentTimeMillis());
            try {
                Thread.sleep(1000); // 任务执行1秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 2, 3, TimeUnit.SECONDS);

        // 延迟2秒后，每3秒执行一次任务（固定延迟）
        scheduler.scheduleWithFixedDelay(() -> {
            System.out.println("固定延迟执行：" + System.currentTimeMillis());
            try {
                Thread.sleep(1000); // 任务执行1秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 2, 3, TimeUnit.SECONDS);
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
