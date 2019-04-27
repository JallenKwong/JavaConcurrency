package com.lun.action.c02;

public class DeamonDemo {
    public static class DaemonT extends Thread{
        @Override
        public void run() {
            while (true){
                System.out.println(Thread.currentThread().getId()+"I'm alive");
                try {
                    Thread.sleep(1000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new DaemonT();
        t1.setDaemon(true);
        t1.start();
        Thread t2 = new DaemonT();
        t2.start();

        Thread.sleep(2000);
        t2.stop();//如果没有此行 则主线程和两个用户线程中，主线程结束，但是用户线程t2并没有结束，则t1作为守护线程，不会结束

    }
}
