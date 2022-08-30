package com.yfkyplatform.parkinglotmiddleware.universal;

import cn.hutool.core.util.StrUtil;
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
    void testNew() {
        TaskChainBuilder<TestData> taskChain = new TaskChainBuilder<>((data) -> System.out.println(data));
        taskChain.use((testData) -> {
            testData.setValue(2);
            System.out.println("task1 value:" + testData.getValue());
        }).use((testData, next) -> {
            if (StrUtil.equals(testData.getId(), "No")) {
                next.accept(testData);
            } else {
                testData.setValue(3);
                System.out.println("task2 value:" + testData.getValue());
                next.accept(testData);
            }
        }).use((testData) -> {
            testData.setValue(4);
            System.out.println("task3 value:" + testData.getValue());
        });
        TestData data = new TestData();
        data.setId("TaskChainTest");
        data.setValue(0);
        System.out.println("TaskChainTest run");
        taskChain.work(data);

        TestData data2 = new TestData();
        data2.setId("No");
        data2.setValue(0);
        System.out.println("No run");
        taskChain.work(data2);
    }

    @Data
    class TestData {
        private String id;
        private int value;
    }
}
