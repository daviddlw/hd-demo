package com.david.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.ListeningScheduledExecutorService;
import com.google.common.util.concurrent.MoreExecutors;

public class GuavaConcurrentTest {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static void main(String[] args) {
		testScheduledTask();
	}

	private static void testScheduledTask() {
		ListeningScheduledExecutorService exec = MoreExecutors.listeningDecorator(Executors.newScheduledThreadPool(4));
		exec.schedule(new Runnable() {

			@Override
			public void run() {
				System.out.println("do query api with frontedge program: " + sdf.format(new Date()));
				for (int i = 0; i < 100; i++) {
					System.out.println("query for count: " + (++i));
				}
			}
		}, 5, TimeUnit.SECONDS);
		System.out.println("now: " + sdf.format(new Date()));
	}

}
