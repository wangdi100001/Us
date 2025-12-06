package com.example.center.thread;

import lombok.Getter;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NumberLock {

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
