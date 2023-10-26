package com.yfkyplatform.parkinglotmiddleware.universal.tool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * 异步任务管理
 *
 * @author Suhuyuan
 */
@Slf4j
@Component
public class ListTaskAsyncManager {
    private final Executor workPool;

    private ListTaskAsyncManager() {
        workPool = Executors.newFixedThreadPool(64);
    }

    public <TData> CompletableFuture<Void> createTaskAndRun(Consumer<TData> taskFunction, List<TData> data) {
        return CompletableFuture.runAsync(() -> {
            List<CompletableFuture<Void>> workTasks = new LinkedList<>();
            log.info("开始运行任务！共有" + data.size() + "个任务");
            data.forEach(item -> workTasks.add(CompletableFuture.runAsync(() -> taskFunction.accept(item), workPool)));
            CompletableFuture.allOf(workTasks.toArray(new CompletableFuture[]{})).join();
            log.info("完成所有任务");
        });
    }
}
