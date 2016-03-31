package com.david.demo;

import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import com.google.common.util.concurrent.RateLimiter;

public class TestTPS {
	static long start;
	static AtomicInteger doneCounter = new AtomicInteger(0);
	static int joblimit = 200;
	static int TPS = 50;

	public static void main(String[] args) {
		RateLimiter rateLimiter = RateLimiter.create(TPS);

		ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		start = System.currentTimeMillis();

		for (int i = 0; i < joblimit; i++) {
//			rateLimiter.acquire(); //thread wait for the permits
			executorService.execute(new Runnable() {

				@Override
				public void run() {
					System.out.println("doing by " + Thread.currentThread().getName());
					int index = doneCounter.incrementAndGet();
					try {
						TimeUnit.MILLISECONDS.sleep(200);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if (index == joblimit) {
						System.out.println(joblimit + " job is completed in " + (System.currentTimeMillis() - start)
								+ " msec with " + TPS + " tps");
					}
				}
			});
		}
	}
}
