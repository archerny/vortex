package com.vortex.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Async execution configuration.
 *
 * Enables Spring @Async support and defines a dedicated thread pool
 * for broker sync tasks so that HTTP request threads are not blocked
 * by long-running operations (e.g., IBKR Flex Query polling).
 */
@Configuration
@EnableAsync
public class AsyncConfig {

    private static final Logger logger = LoggerFactory.getLogger(AsyncConfig.class);

    /**
     * Thread pool for broker sync tasks.
     *
     * <ul>
     *   <li>corePoolSize=2 — enough for normal concurrent syncs</li>
     *   <li>maxPoolSize=4  — burst capacity</li>
     *   <li>queueCapacity=10 — bounded queue to avoid unbounded memory growth</li>
     *   <li>rejectedHandler — logs and rejects when the queue is full</li>
     * </ul>
     */
    @Bean("syncTaskExecutor")
    public TaskExecutor syncTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(4);
        executor.setQueueCapacity(10);
        executor.setThreadNamePrefix("sync-");
        executor.setRejectedExecutionHandler(syncRejectedHandler());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        executor.initialize();
        logger.info("Initialized syncTaskExecutor: core={}, max={}, queue={}",
                executor.getCorePoolSize(), executor.getMaxPoolSize(), 10);
        return executor;
    }

    /**
     * Rejected execution handler that logs a warning instead of silently dropping tasks.
     */
    private RejectedExecutionHandler syncRejectedHandler() {
        return (Runnable r, ThreadPoolExecutor pool) -> {
            logger.error("Sync task rejected — thread pool exhausted. "
                    + "activeCount={}, poolSize={}, queueSize={}",
                    pool.getActiveCount(), pool.getPoolSize(), pool.getQueue().size());
            throw new java.util.concurrent.RejectedExecutionException(
                    "Sync task rejected: thread pool and queue are full");
        };
    }
}
