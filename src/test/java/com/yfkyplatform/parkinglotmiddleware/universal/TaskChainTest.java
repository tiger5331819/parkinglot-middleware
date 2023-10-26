package com.yfkyplatform.parkinglotmiddleware.universal;

import cn.hutool.core.util.StrUtil;
import com.yfkyplatform.parkinglotmiddleware.universal.tool.TaskChain;
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
        TaskChain<TestData> taskChain = TaskChain.<TestData>builder((item) -> System.out.println())
                .use((testData) -> {
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
                }).build();
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

    @Test
    void testNew2() {
        Integer ttt = 123;
        TaskChain<Integer> taskChain = TaskChain.<Integer>builder(System.out::println, true)
                .use((testData) -> {
                    System.out.println(testData);
                })
                .use((testData) -> {
                    testData = 67890;

                })
                .build();
        taskChain.work(ttt);
        System.out.println(ttt);
    }

    @Data
    class TestData {
        private String id;
        private int value;
    }
}
