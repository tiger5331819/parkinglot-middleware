package com.yfkyplatform.parkinglotmiddleware.universal.migration;

import com.yfkyplatform.parkinglotmiddleware.universal.tool.ListTaskAsyncManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * ETL管理器
 *
 * @author Suhuyuan
 */
@Slf4j
@Component
public class MigrationManager<OldData,NewData> {
    private final MigrationTask<OldData,NewData> migrationTask;

    private final ListTaskAsyncManager listTaskAsyncManager;

    public MigrationManager(ListTaskAsyncManager listTaskAsyncManager){
        this.listTaskAsyncManager = listTaskAsyncManager;
        migrationTask= new MigrationTask<>();
    }

    public MigrationManager<OldData,NewData> extract(Supplier<List<OldData>> service){
        migrationTask.createExtract(service);
        return this;
    }

    public MigrationManager<OldData,NewData> transform(Function<OldData,NewData> service){
        migrationTask.createTransform(service);
        return this;
    }

    public MigrationManager<OldData,NewData> load(Consumer<NewData> service){
        migrationTask.createLoad(service);
        return this;
    }

    public CompletableFuture<Void> run(){
        List<OldData> oldDataList=migrationTask.extractService().get();
        return listTaskAsyncManager.createTaskAndRun((oldData)->{
            NewData newData=migrationTask.transformService().apply(oldData);
            migrationTask.loadService().accept(newData);
        },oldDataList);
    }
}
