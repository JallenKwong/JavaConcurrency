package com.lun.action.c02;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ArrayListMultiThread {
//    static ArrayList<Integer> al = new ArrayList<Integer>(10);
    static Collection<Integer> al = Collections.synchronizedCollection(new ArrayList<Integer>(10));

    public static class AddThread implements Runnable{
        @Override
        public void run() {
        	//synchronized (AddThread.class) {
        		for (int i = 0; i < 1000000; i++) {
        			al.add(i);
        		}
			//}
        }
    }

    public static void main(String[] args) throws Exception{
        Thread t1 = new Thread(new AddThread());
        Thread t2 = new Thread(new AddThread());
        t1.start();
        t2.start();
        t1.join(); t2.join();
        System.out.println(al.size());
    }
}

/*
Exception in thread "Thread-0" java.lang.ArrayIndexOutOfBoundsException: 15
	at java.util.ArrayList.add(ArrayList.java:463)
	at com.lun.action.c02.ArrayListMultiThread$AddThread.run(ArrayListMultiThread.java:12)
	at java.lang.Thread.run(Thread.java:748)
1000007
*/
