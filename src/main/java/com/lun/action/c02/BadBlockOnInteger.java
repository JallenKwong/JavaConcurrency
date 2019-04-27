package com.lun.action.c02;

public class BadBlockOnInteger implements Runnable{
    public static Integer i = 0;

    static BadBlockOnInteger instance= new BadBlockOnInteger();

    @Override
    public void run() {
        for (int j = 0; j < 1000000; j++) {
            synchronized (this){
                i++;
            }
//            synchronized (i){//对各种各样的i进行加锁
//            	i++;
//            }
        }
    }

    public static void main(String[] args) throws Exception{
        Thread t1 = new Thread(instance);
        Thread t2 = new Thread(instance);
        t1.start(); t2.start();

        t1.join(); t2.join();

        System.out.println(i);
    }

}
