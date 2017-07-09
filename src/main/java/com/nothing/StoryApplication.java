package com.nothing;

import com.nothing.retrofit.autoconfigure.RetrofitClientsScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RetrofitClientsScan(basePackages = "com.nothing.story.client")
public class StoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(StoryApplication.class, args);
	}
}
