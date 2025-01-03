package com.biology.admin.controller.manage;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.biology.common.core.base.BaseController;
import com.biology.common.core.dto.ResponseDTO;
import com.biology.domain.manage.gosip.db.GosipClientService;
import com.biology.domain.manage.gosip.dto.channels.ChannaelListDTO;
import com.biology.domain.manage.gosip.dto.channels.ChannelQuery;
import com.biology.domain.manage.gosip.dto.streams.ChannelStreamQueryDTO;
import com.biology.domain.manage.gosip.dto.streams.ChannelStreamsDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "视频第三方对接", description = "视频第三方对接")
@RestController
@RequestMapping("/manage/wvp")
@Validated
@RequiredArgsConstructor
public class GosipController extends BaseController {

    // private final WebClient webClient;

    private final GosipClientService gosipClientService;

    @GetMapping("/streams")
    public ResponseDTO<ChannelStreamsDTO> getStream(ChannelStreamQueryDTO queryDTO) {
        return ResponseDTO.ok(gosipClientService.getStreamsByChannelId(queryDTO));
    }

    @GetMapping("/channels")
    public ResponseDTO<ChannaelListDTO> getChannels(ChannelQuery queryDTO) {
        return ResponseDTO.ok(gosipClientService.getChannels(queryDTO));
    }

}
