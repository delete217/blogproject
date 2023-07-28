package com.delete.blogg.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class ThreadPoolConfig {
    @Bean("taskExecutor")
    public Executor asyncServiceExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(5);

        taskExecutor.setMaxPoolSize(20);

        taskExecutor.setQueueCapacity(Integer.MAX_VALUE);

        taskExecutor.setKeepAliveSeconds(60);

        taskExecutor.setThreadNamePrefix("码神之路在线博客");

        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);

        taskExecutor.initialize();
        return taskExecutor;
    }

}
