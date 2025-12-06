package com.example.center.thread;

import lombok.Getter;

public class NumberSynchronized {

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
