package com.lun.action.c02;

public class PriorityDemo {
    public static class HighPriority extends Thread{
        static int count = 0;

        @Override
        public void run() {
            while (true){
                synchronized (PriorityDemo.class){
                    count++;
                    if (count > 100000000){
                        System.out.println("HighPriority is complete.");
                        break;
                    }
                }
            }
        }
    }

    public static class LowPriority extends Thread{
        static int count = 0;

        @Override
        public void run() {
            while (true){
                synchronized (PriorityDemo.class){
                    count++;
                    if(count > 100000000){
                        System.out.println("LowPriority is complete.");
                        break;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        HighPriority hi = new HighPriority();
        LowPriority lo = new LowPriority();
        hi.setPriority(Thread.MAX_PRIORITY);
        lo.setPriority(Thread.MIN_PRIORITY);
        hi.start();
        lo.start();
    }
}
