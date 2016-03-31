package com.david.demo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.david.dto.User;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.google.common.util.concurrent.RateLimiter;
import com.google.common.util.concurrent.Service.Listener;

public class MainRun {
	public static void main(String[] args) {
		// testListeningExecutorService();
		// testUser();
		// testPayRateLimiter();
//		testNoRateLimiter();
		// testRateLimiter();
	}

	public static void testNoRateLimiter() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			System.out.println("call execute task-" + i);
		}

		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

	public static void testRateLimiter() {
		RateLimiter r = RateLimiter.create(100);
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
//			r.acquire();
			System.out.println("call execute task-" + i);
		}

		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}

	public static void testPayRateLimiter() {
		System.out.println(Runtime.getRuntime().availableProcessors());
		// ScheduledExecutorService scheduledExec =
		// Executors.newSingleThreadScheduledExecutor();

		System.out.println("================================");
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		long start = System.currentTimeMillis();
		ExecutorService service = Executors.newCachedThreadPool();
		ListeningExecutorService lf = MoreExecutors.listeningDecorator(service);

		RateLimiter r = RateLimiter.create(5);
		long begin = System.currentTimeMillis();
		for (int i = 0; i < 20; i++) {
			r.acquire();
			// System.err.println("rate: " + r.getRate());
			ListenableFuture<String> result = lf.submit(new PayTestDTO("david-" + i, begin));
			Futures.addCallback(result, new FutureCallback<String>() {

				@Override
				public void onFailure(Throwable ex) {
					ex.printStackTrace();
				}

				@Override
				public void onSuccess(String message) {
					System.out.println(String.format("task: [%s], %s", message, sdf.format(new Date())));
				}
			});
		}

		double result = (System.currentTimeMillis() - start) / 1000.0;
		System.out.println("service timeout, timecost: " + result);

	}

	public static void testGuavaMap() {
		Map<String, Integer> guavaMap = Maps.newConcurrentMap();
		RateLimiter rateLimiter = RateLimiter.create(30);
		Listener listener;
	}

	private static void testUser() {
		User user = new User();
		user.setId(1);
		user.setName("daviddai");
		System.out.println(user);
	}

	private static void testListeningExecutorService() {
		ListeningExecutorService service = MoreExecutors
				.listeningDecorator(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
		ListenableFuture<String> future = service.submit(new Callable<String>() {

			@Override
			public String call() throws Exception {
				System.out.println("start to wait 1 seconds...");
				TimeUnit.SECONDS.sleep(1);
				return "hello, david-" + System.currentTimeMillis();
			}
		});

		Futures.addCallback(future, new FutureCallback<String>() {

			@Override
			public void onFailure(Throwable arg0) {
				System.err.println(arg0.getMessage());
			}

			@Override
			public void onSuccess(String arg0) {
				System.out.println("arg0 is the callback result: " + arg0);
				System.out.println("success-" + arg0);
			}
		});

		try {
			TimeUnit.SECONDS.sleep(3);
			System.out.println("after 3 seconds waiting...");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
