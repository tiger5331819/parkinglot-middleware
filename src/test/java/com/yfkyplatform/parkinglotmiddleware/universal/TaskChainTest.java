package com.yfkyplatform.parkinglotmiddleware.universal;

import com.yfkyplatform.parkinglotmiddleware.universal.middleware.TaskChainBuilder;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * @author Suhuyuan
 */
@SpringBootTest
public class TaskChainTest {

    @Test
    void test() {
        TaskChainBuilder<TestData> taskChain = new TaskChainBuilder<>((data) -> {
            System.out.println(data);
        });
        taskChain.use((testData) -> {
            testData.setValue(2);
            System.out.println("task1 value:" + testData.getValue());
        }).use((testData) -> {
            testData.setValue(3);
            System.out.println("task1 value:" + testData.getValue());
        }).use((testData) -> {
            testData.setValue(4);
            System.out.println("task1 value:" + testData.getValue());
        });
        TestData data = new TestData();
        data.setId("TaskChainTest");
        data.setValue(0);
        taskChain.work(data);
    }

    @Data
    class TestData {
        private String id;
        private int value;
    }
}
