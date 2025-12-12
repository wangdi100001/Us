package com.example.center.thread;

import lombok.Synchronized;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

class MyDeadLock implements Runnable {

    private String lockA;
    private String lockB;

    public MyDeadLock(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {

        synchronized (lockA){
            System.out.println(Thread.currentThread().getName() + " === 自己持有: " + lockA+", 尝试去获取lockB: " + lockB);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            synchronized (lockB){
                System.out.println(Thread.currentThread().getName() + " 自己持有: " + lockB+", 尝试去获取lockA: " + lockA);
            }
        }

    }
}

public class DeadLockDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        Thread threadA = new Thread(new MyDeadLock("lockA", "lockB"),"线程A");
        Thread threadB = new Thread(new MyDeadLock("lockB", "lockA"),"线程B");
        threadA.start();
        threadB.start();
    }

}
