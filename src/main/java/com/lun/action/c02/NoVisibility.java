package com.lun.action.c02;

/**
 * 需通过设置参数 -server 切换模式才能看出差异
 * 
 * client模式下，由于优化不够，程序较久退出
 * 
 * @author 白居布衣
 *
 */
public class NoVisibility {
	private static volatile boolean ready;
	
	private static  int number;
	
	private static class ReaderThread extends Thread{
		@Override
		public void run() {
			while(!ready);
			System.out.println(number);
		}
	}

	public static void main(String[] args)throws InterruptedException {
		new ReaderThread().start();
		Thread.sleep(1000);
		number =42;
		ready = true;
		Thread.sleep(5000);
	}
}
