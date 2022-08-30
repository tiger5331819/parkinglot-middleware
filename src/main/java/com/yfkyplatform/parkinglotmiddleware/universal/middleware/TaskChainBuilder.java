package com.yfkyplatform.parkinglotmiddleware.universal.middleware;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 中间件建造器
 *
 * @author Suhuyuan
 */

public class TaskChainBuilder<TData> {
    private final List<Function<Consumer<TData>, Consumer<TData>>> middlewareList;
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

    public TaskChainBuilder<TData> use(Function<Consumer<TData>, Consumer<TData>> chain) {
        this.middlewareList.add(chain);
        return this;
    }

    public TaskChainBuilder<TData> use(BiConsumer<TData, Consumer<TData>> chain) {
        use((next) -> (data) -> chain.accept(data, next));
        return this;
    }

    public TaskChainBuilder<TData> use(Consumer<TData> chain) {
        use((next) -> (data) -> {
            chain.accept(data);
            next.accept(data);
        });
        return this;
    }

    public void work(TData data) {
        Consumer<TData> request = completeTask;
        List<Function<Consumer<TData>, Consumer<TData>>> workList = new ArrayList<>(middlewareList);
        Collections.reverse(workList);
        for (Function<Consumer<TData>, Consumer<TData>> middleware : workList) {
            request = middleware.apply(request);
        }
        request.accept(data);
    }
}
