package com.lun.action.c03;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class DivTask implements Runnable{

	private int a,b;

	public DivTask(int a, int b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public void run() {
		double re = a / b;
		System.out.println(re);
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		//ExecutorService es = Executors.newCachedThreadPool();
		ExecutorService es = new TraceThreadPoolExecutor(0, Integer.MAX_VALUE, 0, TimeUnit.SECONDS,
				new SynchronousQueue<>());
		
		for(int i = 0;i < 5;i++) {
			es.execute(new DivTask(100, i));
//			es.submit(new DivTask(100, i));
//			Future<?> ft = es.submit(new DivTask(100, i));
//			ft.get();
		}
		
		es.shutdown();
	}
	
	
}
