package com.yfkyplatform.parkinglotmiddleware.universal.migration;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 默认迁移任务
 *
 * @author Suhuyuan
 */

public class MigrationTask<OldData,NewData> {
    private int size=1;
    private Supplier<List<OldData>> defaultExtract;
    private Function<OldData,NewData> defaultTransform;
    private Consumer<NewData> defaultLoad;

    public void createDataSize(int size){
        this.size=size;
    }
    public void createExtract(Supplier<List<OldData>> service){
        defaultExtract=service;
    }

    public void createTransform(Function<OldData,NewData> service){
        defaultTransform=service;
    }

    public void createLoad(Consumer<NewData> service){
        defaultLoad=service;
    }

    /**
     * 数据大小
     */
    public int dataSize() {
        return size;
    }

    /**
     * 源数据抽取
     *
     * @return
     */
    public Supplier<List<OldData>> extractService() {
        return defaultExtract;
    }

    /**
     * 数据转换
     *
     * @return
     */
    public Function<OldData,NewData> transformService() {
        return defaultTransform;
    }

    /**
     * 新数据加载
     */
    public Consumer<NewData> loadService() {
        return defaultLoad;
    }
}
