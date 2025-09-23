package com.biology.domain.manage.gosip.db;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.alibaba.druid.wall.violation.ErrorCode;
import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.common.utils.jackson.JacksonUtil;
import com.biology.domain.manage.gosip.dto.channels.ChannaelListDTO;
import com.biology.domain.manage.gosip.dto.channels.ChannaelListRootDTO;
import com.biology.domain.manage.gosip.dto.channels.ChannelDTO;
import com.biology.domain.manage.gosip.dto.channels.ChannelFilterDTO;
import com.biology.domain.manage.gosip.dto.channels.ChannelQuery;
import com.biology.domain.manage.gosip.dto.streams.ChannelStreamQueryDTO;
import com.biology.domain.manage.gosip.dto.streams.ChannelStreamsDTO;
import com.biology.domain.manage.gosip.dto.streams.RootDTO;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GosipClientService {
    private final WebClient gosipClient;

    public ChannelStreamsDTO getStreamsByChannelId(ChannelStreamQueryDTO query) {
        if (StrUtil.isBlank(query.getId())) {
            throw new ApiException(Business.COMMON_UNSUPPORTED_OPERATION);
        }

        Mono<RootDTO> mono = null;
        try {
            mono = gosipClient.post()
                    .uri("/channels/" + query.getId() + "/streams")
                    .retrieve()
                    .bodyToMono(RootDTO.class);
            // 判断是否请求成功
        } catch (Exception e) {
            throw new ApiException(Business.VIDERO_CHANNEL_OPERATION);
        }
        if (mono.block().getCode() == "1002") {
            throw new ApiException(Business.VIDERO_CHANNEL_OPERATION);
        }
        return mono.block().getData();
    }

    public ChannaelListDTO getChannels(ChannelQuery query) {

        try {
            Mono<ChannaelListRootDTO> mono = gosipClient.get().uri(uriBuild -> uriBuild.path("/channels")
                    .queryParam("limit", query.getLimit())
                    .queryParam("skip", query.getSkip())
                    .queryParam("name", query.getName())
                    .build())
                    .header("accept", "application/json")
                    .retrieve()
                    .bodyToMono(ChannaelListRootDTO.class);

            if (mono.block().getCode() == "1002") {
                throw new ApiException(Business.VIDERO_CHANNEL_OPERATION);
            }
            return mono.block().getData();

        } catch (Exception e) {
            // throw new ApiException(Business.VIDERO_CHANNEL_OPERATION);
            ChannaelListDTO channaelListDTO = new ChannaelListDTO();
            channaelListDTO.setTotal(10);
            channaelListDTO.setList(new ArrayList<>());
            for (int i = 0; i < 10; i++) {
                ChannelDTO channelDTO = new ChannelDTO();
                channelDTO.setName("test" + i);
                channaelListDTO.getList().add(channelDTO);
            }
            return channaelListDTO;
        }

    }
}
