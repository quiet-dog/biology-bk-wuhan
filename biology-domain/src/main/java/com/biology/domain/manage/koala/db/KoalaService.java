package com.biology.domain.manage.koala.db;

import java.net.ConnectException;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.biology.common.exception.ApiException;
import com.biology.common.exception.error.ErrorCode.Business;
import com.biology.domain.manage.gosip.dto.channels.ChannaelListRootDTO;
import com.biology.domain.manage.koala.dto.KoalaResDTO;
import com.biology.domain.manage.koala.dto.auth.LoginReqDTO;
import com.biology.domain.manage.koala.dto.auth.LoginResDTO;
import com.biology.domain.manage.koala.dto.event.KoalaEventDTO;
import com.biology.domain.manage.koala.dto.event.KoalaEventQuery;
import com.biology.domain.manage.koala.dto.records.KoalaRecordResponseDTO;
import com.biology.domain.manage.koala.dto.records.KoalaRecordsQuery;
import com.biology.domain.manage.koala.dto.subject.SubjectDTO;
import com.biology.domain.manage.koala.dto.subject.SubjectQuery;
import com.biology.infrastructure.config.KoalaClientConfig;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class KoalaService {
    private final WebClient koalaClient;

    private final KoalaClientConfig koalaClientConfig;

    private static String AUTH_TOKEN;

    public KoalaRecordResponseDTO getRecords(KoalaRecordsQuery query) {
        try {
            Mono<KoalaRecordResponseDTO> result = koalaClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/attendance/records")
                            .queryParams(query.toMap())
                            .build())
                    .header("Authorization", AUTH_TOKEN)
                    .header("user-agent", "Koala Admin")
                    .retrieve()
                    .bodyToMono(KoalaRecordResponseDTO.class);
            return result.block(); // 阻塞等待返回
        } catch (Exception e) {
            // 服务不可用，连接失败
            System.out.println(e.getMessage());
            throw new ApiException(Business.KOALA_SERVICE_UNAVAILABLE, e);
        }
    }

    public LoginResDTO auth() {
        try {
            LoginReqDTO reqDTO = new LoginReqDTO();
            reqDTO.setUsername(koalaClientConfig.getUsername());
            reqDTO.setPassword(koalaClientConfig.getPassword());
            reqDTO.setAuth_token(true);
            Mono<KoalaResDTO<LoginResDTO>> mo = koalaClient.post().uri(uriBuilder -> uriBuilder
                    .path("/auth/login")
                    .build())
                    .bodyValue(reqDTO)
                    .header("accept", "application/json")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<KoalaResDTO<LoginResDTO>>() {
                    });
            KoalaResDTO<LoginResDTO> res = mo.block();
            AUTH_TOKEN = res.getData().auth_token;
            return res.getData();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiException(Business.KOALA_SERVICE_UNAVAILABLE, e);
        }

    }

    public KoalaResDTO<List<SubjectDTO>> getSubjectList(SubjectQuery query) {
        try {
            Mono<KoalaResDTO<List<SubjectDTO>>> result = koalaClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/subject/list")
                            .queryParams(query.toMap())
                            .build())
                    .header("Authorization", AUTH_TOKEN)
                    .header("user-agent", "Koala Admin")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<KoalaResDTO<List<SubjectDTO>>>() {
                    });
            // .bodyToMono(String.class);
            return result.block();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiException(Business.KOALA_SERVICE_UNAVAILABLE, e);
        }
    }

    public KoalaResDTO<List<KoalaEventDTO>> getEvents(KoalaEventQuery query) {
        try {
            Mono<KoalaResDTO<List<KoalaEventDTO>>> result = koalaClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/event/events")
                            .queryParams(query.toMap())
                            .build())
                    .header("Authorization", AUTH_TOKEN)
                    .header("user-agent", "Koala Admin")
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<KoalaResDTO<List<KoalaEventDTO>>>() {
                    });
            return result.block();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new ApiException(Business.KOALA_SERVICE_UNAVAILABLE, e);
        }
    }

    public static String getAuthToken() {
        return AUTH_TOKEN;
    }
}
