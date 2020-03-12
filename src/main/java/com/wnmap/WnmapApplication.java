package com.wnmap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
@EnableScheduling
public class WnmapApplication {
	public static ConcurrentHashMap<String, ScheduledFuture> hostTaskMap = new ConcurrentHashMap<String, ScheduledFuture>();
	public static ConcurrentHashMap<String, ScheduledFuture> portTaskMap = new ConcurrentHashMap<String, ScheduledFuture>();
	public static ConcurrentHashMap<String, ScheduledFuture> osTaskMap = new ConcurrentHashMap<String, ScheduledFuture>();

	public static void main(String[] args) {
		SpringApplication.run(WnmapApplication.class, args);
	}
	// 创建线程池
	@Bean
	public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
		ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
		executor.setPoolSize(5);
		executor.setThreadNamePrefix("taskExecutor-");
//		executor.setWaitForTasksToCompleteOnShutdown(true);
//		executor.setAwaitTerminationSeconds(60);
		return executor;
	}

    @Autowired
    InitDb initDb;
    @Autowired
    InitTask initTask;
    
    @Bean
	public CommandLineRunner demo() {
    	return (args) ->{
    		initDb.init();
    		initTask.init();
    	};
    }

}
