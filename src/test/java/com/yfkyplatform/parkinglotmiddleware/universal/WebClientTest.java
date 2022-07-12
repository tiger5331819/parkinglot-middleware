package com.yfkyplatform.parkinglotmiddleware.universal;

import cn.hutool.core.util.ObjectUtil;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carport.ChannelResult;
import com.yfkyplatform.parkinglotmiddleware.carpark.daoer.client.domin.resp.carport.ChannelStateResult;
import com.yfkyplatform.parkinglotmiddleware.domain.manager.container.ability.carport.ChannelInfoResult;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Suhuyuan
 */
@SpringBootTest
public class WebClientTest {

    @Test
    void uriTest() {
        val tt = WebClient.builder().baseUrl("http://122.224.250.35:7013/Parking/Handheld").build();
        Object result = tt.method(HttpMethod.GET).uri("/Login").retrieve().bodyToMono(Object.class).doOnError(WebClientResponseException.class, err -> {
                    String errResult = err.getResponseBodyAsString();
                    System.out.println(errResult);
                    //throw new RuntimeException(errResult);
                })
                .block();

    }

    @Test
    void asyncSendTest() {
        Mono<List<ChannelResult>> result = WebClient.create().method(HttpMethod.GET)
                .uri("http://localhost:8082/api/test/channels")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ChannelResult>>() {
                });

        Mono<List<ChannelStateResult>> result2 = WebClient.create().method(HttpMethod.GET)
                .uri("http://localhost:8082/api/test/channels/state")
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ChannelStateResult>>() {
                });

        List<ChannelInfoResult> result55 = Mono.zip(result, result2, (channelsInfo, channelsStates) -> {
            List<ChannelResult> channelsInfoBody = channelsInfo;
            List<ChannelStateResult> channelsStatesBody = channelsStates;

            return channelsInfoBody.stream().map(channelResult -> {
                ChannelStateResult channelStateResult = channelsStatesBody.stream().filter(item -> item.getChannelId().contains(channelResult.getChannelId())).findFirst().get();
                if (ObjectUtil.isNotNull(channelStateResult)) {
                    ChannelInfoResult data = new ChannelInfoResult();
                    data.setChannelId(channelResult.getChannelId());
                    data.setChannelName(channelResult.getChannelName());
                    data.setType(channelResult.getType());
                    data.setBoard(channelStateResult.getBoard());
                    data.setCamera(channelStateResult.getCamera());
                    data.setDoor(channelStateResult.getDoor());
                    data.setSense(channelStateResult.getSense());
                    return data;
                } else {
                    return null;
                }
            }).collect(Collectors.toList());
        }).block();

        result.block().forEach(item -> System.out.println(item));
        result2.block().forEach(item -> System.out.println(item));
    }
}
