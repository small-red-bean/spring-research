package com.redbean.springresearch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.concurrent.*;


@SpringBootApplication
@EnableScheduling
public class SpringResearchApplication {

	public static void main(String[] args) {
        SpringApplication.run(SpringResearchApplication.class, args);
//        int[][] s = new int[3][];
//            s[0] = new int[]{1,2,3};
//            s[1] = new int[]{1,2,3,4};
//            s[2] = new int[]{1,2,3,4,8,9,10};
//            System.out.println(s[1][3]);
    }
}
