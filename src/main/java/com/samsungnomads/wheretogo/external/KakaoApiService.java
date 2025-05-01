package com.samsungnomads.wheretogo.external;

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
import java.util.concurrent.ThreadLocalRandom;


@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoApiService {

    private final RestTemplate restTemplate;

    @Value("${kakao.api.url}")
    private String kakaoApiUrl;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    public List<KakaoApiResponseDto.Document> fetchPlaceList(String stationName, String category) {

        String url = UriComponentsBuilder.fromUriString(kakaoApiUrl)
                .queryParam("query", stationName + " " + category)
                .queryParam("page", ThreadLocalRandom.current().nextInt(1, 46))
                .queryParam("size", 5)
                .build()
                .toUriString();

        // request
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoApiKey);
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        // response
        ResponseEntity<KakaoApiResponseDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                KakaoApiResponseDto.class
        );

        return response.getBody().getDocuments();
    }
}
