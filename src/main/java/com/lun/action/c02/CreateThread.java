package com.lun.action.c02;

public class CreateThread {

	public static void main(String[] args) {
		
		Thread t1 = new Thread(){
			@Override
			public void run(){
				System.out.println("Hello");
			}
		};
		t1.start();
		
		
		Thread t2 = new Thread(new Runnable(){
			@Override
			public void run(){
				System.out.println("Hello");
			}
		});
		t2.start();
		
		
		Thread t3 = new Thread(() -> System.out.println("Hello"));
		t3.start();
	}

}
