package com.yfkyplatform.parkinglotmiddleware;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yfkyplatform.parkinglotmiddleware.domain.service.ParkingLotManagerEnum;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Suhuyuan
 */
@SpringBootTest
public class MyTest {

    @Autowired
    private RestTemplate restTemplate;


    @ParameterizedTest
    @CsvSource({
            "KR148",
            "KR149",
            "KR159"
    })
    void parameterTest(String carNo) {
        System.out.println("粤B" + carNo);
    }

    @Test
    public void Test1() {
        ParkingLotManagerEnum data = ParkingLotManagerEnum.fromCode(null);
        if (Objects.requireNonNull(data) == ParkingLotManagerEnum.Daoer) {
        } else {
            System.out.println("123");
        }
    }

    @ParameterizedTest
    @CsvSource({"b8754039bbf911edad48000c29bdcc13",
            "c7200e02bbfa11edad48000c29bdcc13",
            "f2ec6b24bbff11edad48000c29bdcc13"})
    void ttttt(String tt) {
        byte[] bs = tt.getBytes();
        long value = 0;
        for (int count = 0; count < 12; ++count) {
            int shift = count << 3;
            value |= ((long) 255 << shift) & ((long) bs[count] << shift);
        }
        System.out.println("1结果：" + value);

        for (int count = 0; count < 12; ++count) {

            value |= bs[count];
        }
        System.out.println("2结果：" + value);

        for (int count = 0; count < 12; ++count) {

            value &= bs[count];
        }
        System.out.println("3结果：" + value);
    }

    @Test
    public void dateTimeTest() {
        System.out.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")));
    }


    @Test
    public void cleanCar() throws JsonProcessingException {
        Mono<String> result = WebClient.create().method(HttpMethod.POST)
                .uri("https://mgnt-pc.q-parking.com/api/mgntpc/pc/order/page")
                .headers(httpHeaders -> {
                    httpHeaders.add("Authorization", "Bearer d748e05de5e94873afcadc139a0e407c");
                    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                })
                .bodyValue("{\"parkinglotId\":\"2007002200000002\",\"orderPayState\":1000,\"chargeTimeStart\":\"2023-01-11 00:00:00\",\"chargeTimeClose\":\"2023-03-21 23:59:59\",\"plateNumberNotNull\":true,\"current\":1,\"size\":10000}")
                .retrieve()
                .bodyToMono(String.class);

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> resultJson = mapper.readValue(result.block(), new TypeReference<Map<String, Object>>() {
        });

        Map<String, Object> data = (Map<String, Object>) resultJson.get("data");
        List<Map<String, Object>> records = (List<Map<String, Object>>) data.get("records");
        List<Long> orderId = records.stream().map(item -> (Long) item.get("orderId")).collect(Collectors.toList());
        System.out.println(orderId);
        orderId.forEach(item -> {
            Mono<String> result2 = WebClient.create().method(HttpMethod.POST)
                    .uri("https://mgnt-pc.q-parking.com/api/mgntpc/pc/order/outsideClean")
                    .headers(httpHeaders -> {
                        httpHeaders.add("Authorization", "Bearer d748e05de5e94873afcadc139a0e407c");
                        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
                    })
                    .bodyValue("{\"explain\":\"人工清场，取消订单\",\"orderId\":" + item + "}")
                    .retrieve()
                    .bodyToMono(String.class);
            result2.block();
        });
    }

    @Test
    public void ttt3() {
        Boolean ttt = null;
        boolean tttee = Boolean.TRUE.equals(ttt);
        System.out.println(tttee);
    }

    @Test
    public void ttt4() {
        int retryCount = 0;
        do {
            System.out.println(retryCount);
        } while (++retryCount < 3);
    }
}
