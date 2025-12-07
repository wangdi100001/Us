package com.example.center.thread;

import lombok.Getter;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Thread01 {

    public static void main(String[] args) {
        shareResourceSynchronized number = new shareResourceSynchronized();
//        NumberLock number = new NumberLock();

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    number.incre();
                }
            },"线程A").start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(()-> {
                number.deIncre();
            },"线程B").start();
        }

    }

}


class shareResourceSynchronized {

    @Getter
    private int number = 0;

    public synchronized void incre(){

        try{
            while(number != 0){
                wait();
            }
            number++;
            System.out.println(Thread.currentThread().getName()+":"+number);
            notifyAll();
        }catch (Exception e) {

        }
    }

    public synchronized void deIncre(){

        try{
            while(number == 0){
                wait();
            }
            number--;
            System.out.println(Thread.currentThread().getName()+":"+number);
            notifyAll();
        }catch (Exception e) {

        }
    }

}

class shareResourceLock {

    @Getter
    private int number = 0;

    Lock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void incre(){

        try{
            lock.lock();
            while(number != 0){
                condition.await();
            }
            number++;
            System.out.println(Thread.currentThread().getName()+":"+number);
            condition.signalAll();
        }catch (Exception e) {

        }finally {
            lock.unlock();
        }
    }

    public void deIncre(){

        try{
            lock.lock();
            while(number == 0){
                condition.await();
            }
            number--;
            System.out.println(Thread.currentThread().getName()+":"+number);
            condition.signalAll();
        }catch (Exception e) {

        }finally {
            lock.unlock();
        }
    }

}
