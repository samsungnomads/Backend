package com.samsungnomads.wheretogo.external;

import com.samsungnomads.wheretogo.domain.line.Line;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class KricApiService {
    private final RestTemplate restTemplate;

    @Value("${kric.api.url}")
    private String kricApiUrl;

    @Value("${kric.api.key}")
    private String kricApiKey;

    public List<KricApiResponseDto.StationInfo> fetchStationInfoList(Line line) {

        String url = UriComponentsBuilder.fromUriString(kricApiUrl)
                .queryParam("serviceKey", "$2a$10$.P03IDI0V3/IewJhrarByeBimfbBi.doplIJyt9zJ/Ta5h0j9HQPi")
                .queryParam("format", "json")
                .queryParam("mreaWideCd", "01")
                .queryParam("lnCd", line.getCode())
                .toUriString();

        // request
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // response
        try {
            ResponseEntity<KricApiResponseDto> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    entity,
                    KricApiResponseDto.class
            );

            KricApiResponseDto responseDto = response.getBody();

            log.warn("[KRIC] kricApiUrl {}", kricApiUrl);
            log.warn("[KRIC] kricApiKey {}", kricApiKey);

            if (responseDto == null) {
                log.warn("[KRIC] 응답 자체가 null 입니다. lineCode: {}", line.getCode());
                return List.of();
            }

            if (responseDto.getBody() == null) {
                log.warn("[KRIC] 응답 body, List<StationInfo>가 null 입니다. lineCode: {}", line.getCode());
                //log.warn("[KRIC] kricApiUrl {}", kricApiUrl);
                //log.warn("[KRIC] kricApiKey {}", kricApiKey);

                return List.of();
            }

            return responseDto.getBody();

            // 외부 API 서버와 통신 자체가 안된 경우
        } catch (Exception e) {
            log.error("[KRIC] API 호출 중 예외 발생. lineCode: {}, message: {}", line.getCode(), e.getMessage(), e);
            return null;
        }


    }

}
