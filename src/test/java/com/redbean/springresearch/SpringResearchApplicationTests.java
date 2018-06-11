package com.redbean.springresearch;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringResearchApplicationTests {

	@Test
	public void contextLoads() {

	}

	private transient volatile boolean initialized;

	public static void main(String[] args) throws Exception {
		Callable<String> callable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(10000);
				return "hello world";
			}
		};

		FutureTask<String>  futureTask = new FutureTask<String>(callable);
		Thread thread = new Thread(futureTask);
		thread.start();
		System.out.println(futureTask.get());
		System.out.println("===============");
//		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"META-INF/redBean-demo-consumer.xml"});
//		context.start();
	}
}
