package com.example.center.thread;

public class Thread01 {

    public static void main(String[] args) {
        NumberSynchronized number = new NumberSynchronized();

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
