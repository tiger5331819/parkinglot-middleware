package com.parkinglotmiddleware.universal.tool;

import lombok.extern.slf4j.Slf4j;

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
@Slf4j
public class TaskChain<TData> {
    private final List<Function<Consumer<TData>, Consumer<TData>>> middlewareList;
    private final Consumer<TData> completeTask;
    private final boolean throwException;

    TaskChain(List<Function<Consumer<TData>, Consumer<TData>>> middlewareList, Consumer<TData> completeTask, boolean throwException) {
        this.throwException = throwException;
        this.middlewareList = middlewareList;
        this.completeTask = completeTask;
    }

    public static <TData> TaskChain.Builder<TData> builder() {
        return new Builder<>();
    }

    public static <TData> TaskChain.Builder<TData> builder(Consumer<TData> worker) {
        return new Builder<>(worker);
    }

    public static <TData> TaskChain.Builder<TData> builder(Consumer<TData> worker, boolean throwException) {
        return new Builder<>(worker, throwException);
    }

    public void work(TData data) {
        Consumer<TData> request = completeTask;
        List<Function<Consumer<TData>, Consumer<TData>>> workList = new ArrayList<>(middlewareList);
        Collections.reverse(workList);
        for (Function<Consumer<TData>, Consumer<TData>> middleware : workList) {
            request = middleware.apply(request);
        }

        try {
            request.accept(data);
        } catch (Exception ex) {
            if (throwException) {
                throw ex;
            }
            log.error("任务链异常：", ex);
        }

    }

    public static class Builder<TData> {
        private final List<Function<Consumer<TData>, Consumer<TData>>> middlewareList;
        private final Consumer<TData> completeTask;
        private final boolean throwException;

        public Builder() {
            this.middlewareList = new ArrayList<>();
            this.completeTask = (data) -> {
            };
            this.throwException = false;
        }

        public Builder(Consumer<TData> worker) {
            this.middlewareList = new ArrayList<>();
            this.completeTask = worker;
            this.throwException = false;
        }

        public Builder(Consumer<TData> worker, boolean throwException) {
            this.middlewareList = new ArrayList<>();
            this.completeTask = worker;
            this.throwException = throwException;
        }

        public Builder<TData> use(Function<Consumer<TData>, Consumer<TData>> chain) {
            this.middlewareList.add(chain);
            return this;
        }

        public Builder<TData> use(BiConsumer<TData, Consumer<TData>> chain) {
            use((next) -> (data) -> chain.accept(data, next));
            return this;
        }

        public Builder<TData> use(Consumer<TData> chain) {
            use((next) -> (data) -> {
                chain.accept(data);
                next.accept(data);
            });
            return this;
        }

        public TaskChain<TData> build() {
            return new TaskChain<>(middlewareList, completeTask, throwException);
        }
    }
}
