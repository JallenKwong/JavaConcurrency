package com.lun.action.c03;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceDemo {
	public static void main(String args[]) {
//		method1();
		method2();
	}

	/**
	 * scheduleAtFixedRate的用法
	 */
	static void method1() {
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

		scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				try {
					// Thread.sleep(1000);
					Thread.sleep(3000);// If any execution of this task takes longer than its period,
										// then subsequent executions may start late,
										// but will not concurrently execute.
					System.out.println(System.currentTimeMillis() / 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, 0/* initialDelay */, 2/* period */, TimeUnit.SECONDS);
	}

	/**
	 * scheduleWithFixedDelay的用法
	 */
	static void method2() {

		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);

		scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
					System.out.println(System.currentTimeMillis() / 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}, 0/* initialDelay */, 2/* delay */, TimeUnit.SECONDS);
	}

}
