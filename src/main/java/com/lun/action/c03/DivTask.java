package com.lun.action.c03;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	
	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();
		
		for(int i = 0;i < 5;i++) {
//			es.execute(new DivTask(100, i));
			es.submit(new DivTask(100, i));
		}
		
		es.shutdown();
	}
	
	
}
