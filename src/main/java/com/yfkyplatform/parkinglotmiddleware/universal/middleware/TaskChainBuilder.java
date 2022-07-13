package com.yfkyplatform.parkinglotmiddleware.universal.middleware;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

/**
 * 中间件建造器
 *
 * @author Suhuyuan
 */

public class TaskChainBuilder<TData> {
    private final List<Consumer<TData>> middlewareList;
    private final Consumer<TData> completeTask;

    public TaskChainBuilder() {
        this.middlewareList = new ArrayList<>();
        this.completeTask = (data) -> {
        };
    }

    public TaskChainBuilder(Consumer<TData> worker) {
        this.middlewareList = new ArrayList<>();
        this.completeTask = worker;
    }

    public TaskChainBuilder<TData> use(Consumer<TData> chain) {
        this.middlewareList.add(chain);
        return this;
    }

    public void work(TData data) {
        Consumer<TData> request = completeTask;
        Collections.reverse(middlewareList);
        for (Consumer<TData> middleware : middlewareList) {
            request = middleware.andThen(request);
        }
        request.accept(data);
    }
}
