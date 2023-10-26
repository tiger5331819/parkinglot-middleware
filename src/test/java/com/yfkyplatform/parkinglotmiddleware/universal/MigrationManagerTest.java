package com.yfkyplatform.parkinglotmiddleware.universal;

import com.yfkyplatform.parkinglotmiddleware.universal.migration.MigrationManager;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @author Suhuyuan
 */
@SpringBootTest
public class MigrationManagerTest {

    @Autowired
    private MigrationManager<Integer,String> migrationManager;

    @ParameterizedTest
    @CsvSource("10")
    void test(int count) {
        CompletableFuture<Void> migrationTask = migrationManager.extract(() -> {
                    List<Integer> data = new ArrayList<>();
                    for (int i = 0; i < count; i++) {
                        data.add(i);
                    }
                    return data;
                }).transform((oldData) -> "New:" + oldData.toString())
                .load(System.out::println).run();

        migrationTask.join();
    }
}
