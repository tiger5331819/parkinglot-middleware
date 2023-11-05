package com.yfkyplatform.parkinglotmiddleware.universal;

import com.parkinglotmiddleware.universal.tool.ListTaskAsyncManager;
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
public class ListTaskAsyncManagerTest {

    @Autowired
    private ListTaskAsyncManager listTaskAsyncManager;

    @ParameterizedTest
    @CsvSource("10")
    void test(int count) {
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            data.add(i);
        }

        CompletableFuture<Void> listTask = listTaskAsyncManager.createTaskAndRun(System.out::println, data);
        listTask.join();
    }
}
