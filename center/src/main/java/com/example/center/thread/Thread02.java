package com.example.center.thread;

import lombok.Getter;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Thread02 {

    public static void main(String[] args) {
        shareResource source = new shareResource();

        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    source.print5();
                }
            },"线程A").start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(()-> {
                source.print10();
            },"线程B").start();
        }

        for (int i = 0; i < 5; i++) {
            new Thread(()-> {
                source.print15();
            },"线程C").start();
        }

    }

}


class shareResource {

    @Getter
    private int number = 1;
    Lock lock = new ReentrantLock();
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();
    Condition c3 = lock.newCondition();

    public void print5(){

        lock.lock();
        try{
            while(number != 1){
                c1.await();
            }
            number = 2;
            System.out.println(Thread.currentThread().getName()+"打印5次");
            c2.signal();
        }catch (Exception e) {

        }finally {
            lock.unlock();
        }
    }

    public void print10(){

        lock.lock();
        try{
            while(number != 2){
                c2.await();
            }
            number = 3;
            System.out.println(Thread.currentThread().getName()+"打印10次");
            c3.signal();
        }catch (Exception e) {

        }finally {
            lock.unlock();
        }
    }

    public void print15(){

        lock.lock();
        try{
            while(number != 3){
                c3.await();
            }
            number = 1;
            System.out.println(Thread.currentThread().getName()+"打印15次");
            c1.signal();
        }catch (Exception e) {

        }finally {
            lock.unlock();
        }
    }

}


