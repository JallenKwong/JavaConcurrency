package com.lun.action.c02;

public class AccountingVol implements Runnable {
	
    static AccountingVol accountingVol = new AccountingVol();
    
    static volatile int i = 0;
    
    public static void increase(){
        i ++;
    }

    @Override
    public void run() {
        for(int i = 1;i< 10000; i++){
            increase();
        }
    }

    public static void main(String[] args) throws Exception {
        Thread thread1 = new Thread(accountingVol);
        Thread thread2 = new Thread(accountingVol);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("i="+i);
    }

}
