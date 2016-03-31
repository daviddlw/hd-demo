package com.david.demo;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class PayTestDTO implements Callable<String> {

	private String name;
	private Random r;
	private long start;
	private static int jobLimit = 20;
	private static AtomicInteger counter = new AtomicInteger(0);

	public PayTestDTO(String name, long start) {
		super();
		this.name = name;
		this.start = start;
		r = new Random();
	}

	public void bindAndPrePay() throws InterruptedException {
		int seconds = r.nextInt(5);
		TimeUnit.SECONDS.sleep(seconds);
		System.out.println("execute " + seconds + " s - bindAndPay");
	}

	public void pay() throws InterruptedException {
		int seconds = r.nextInt(3);
		TimeUnit.SECONDS.sleep(seconds);
		System.out.println("execute " + seconds + " s - pay");
	}

	@Override
	public String call() throws Exception {
		String result = "";
		try {
			System.out.println("task [" + name + "] has submit");
			int index = counter.incrementAndGet();
			bindAndPrePay();
			pay();

			if (index == jobLimit) {
				System.err.println(jobLimit + " jobs is completed in " + (System.currentTimeMillis() - start));
			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		result = String.format("%s finish pay task progess", name);
		return result;
	}

}
