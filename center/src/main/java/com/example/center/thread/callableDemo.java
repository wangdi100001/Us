package com.example.center.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class Mythread implements Callable<String>{
    @Override
    public String call() throws Exception {
        System.out.println(Thread.currentThread().getName()+",call");
        return "1024";
    }
}

public class callableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask = new FutureTask<>(new Mythread());
        Thread thread = new Thread(futureTask,"AAA");
        thread.start();

//        while(!futureTask.isDone()){}
        System.out.println(futureTask.get());
    }
}
