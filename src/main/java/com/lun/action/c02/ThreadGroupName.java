package com.lun.action.c02;

public class ThreadGroupName implements Runnable {
    public static void main(String[] args) {
        ThreadGroup printGroup = new ThreadGroup("PrintGroup");
        Thread t1 = new Thread(printGroup, new ThreadGroupName(), "T1");
        Thread t2 = new Thread(printGroup, new ThreadGroupName(), "T2");

        t1.start();
        t2.start();

        System.out.println(printGroup.activeCount());
        printGroup.list();
    }


    @Override
    public void run() {
        String groupAndName = Thread.currentThread().getThreadGroup().getName() + "-" +
                Thread.currentThread().getName();

        while (true) {
            System.out.println("I am " + groupAndName);

            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
