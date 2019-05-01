package com.lun.action.c03;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadFactoryDemo {
	
	public static void main(String[] args) throws InterruptedException {
	
		ExecutorService es = new ThreadPoolExecutor(5, 5, 0, TimeUnit.SECONDS
				, new SynchronousQueue<Runnable>()
				,(r)->{Thread t = new Thread(r);
				t.setDaemon(true);
				System.out.println("create " + t);
				return t;});
	
		for(int i = 0; i < 5; i++) {
			es.execute(()->{});
		}
		
		TimeUnit.SECONDS.sleep(2);
		
	}
}
