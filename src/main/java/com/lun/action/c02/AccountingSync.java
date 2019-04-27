package com.lun.action.c02;

public class AccountingSync implements Runnable{
    static int i = 0;
    
    public synchronized void increase(){
        i++;
    }
    
    @Override
    public void run() {
        for(int j=0;j<1000000;j++){
        	increase();//synchronized的可重入性

        	//or
        	
//        	synchronized(this){
//                i++;
//            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        AccountingSync accountingSync = new AccountingSync();
        Thread t1 = new Thread(accountingSync);
        Thread t2 = new Thread(accountingSync);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.print(i);
    }
}
