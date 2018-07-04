package com.redbean.springresearch;

import com.alibaba.fastjson.JSON;
import com.redbean.springresearch.http.Data;
import com.redbean.springresearch.http.HttpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class SpringResearchApplication {

	public static void main(String[] args) {
//		ExecutorService executorService = new ThreadPoolExecutor(4, 4,
//				8000, TimeUnit.MILLISECONDS,
//				new LinkedBlockingQueue<Runnable>(100));
//
//		executorService.execute(new Runnable() {
//			@Override
//			public void run() {
//				System.out.println("========");
//			}
//		});


		SpringApplication.run(SpringResearchApplication.class, args);

		Data data = new Data();
		data.setId(1);

		HttpServer.doPost("http://localhost:9999/demo", JSON.toJSONString(data), true);
	}
}
